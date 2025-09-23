package com.ark.invest_api.service;

import com.ark.invest_api.dto.Fund;
import com.ark.invest_api.exceptions.ConflictException;
import com.ark.invest_api.exceptions.InvalidArgumentException;
import com.ark.invest_api.exceptions.NotFoundException;
import com.ark.invest_api.repository.FundRepository;
import com.ark.invest_api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FundsService {
    @Autowired
    private FundRepository repository;

    @Autowired
    private TransactionRepository transactionRepository;


    public Fund addfunds(Fund fund) {
        if (fund.getName() == null || fund.getName().isBlank()) {
            throw new InvalidArgumentException("name is required");
        }
        String name = fund.getName().trim();
        fund.setName(name);

        // Optional: normalize currency to 3-letter ISO
        if (fund.getCurrency() != null) {
            String cur = fund.getCurrency().trim().toUpperCase();
            if (cur.length() != 3) throw new InvalidArgumentException("currency must be 3 letters (e.g., USD)");
            fund.setCurrency(cur);
        }
        else{
            fund.setCurrency("USD");
        }

        // Fast path: app-level check
        if (repository.existsByNameIgnoreCase(name)) {
            throw new ConflictException("Fund name already exists: '" + name + "'");
        }

        return repository.save(fund);

    }


    public List<Fund> getAllFunds() {

        List<Fund> funds = repository.findAll();
        return funds;
    }


    public Fund get(Long id) {

        return repository.findById(id).orElseThrow(() -> new NotFoundException("Fund not found with id " + id));
    }

    public Fund update(Long id, Fund fund) {

        // 1. Load the existing fund by ID
        Fund f = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fund not found: " + id));
        List<Fund> allFunds = getAllFunds();
        boolean name = allFunds.stream().anyMatch(find -> find.getName().equals(fund.getName()));
        if(name){
            throw  new InvalidArgumentException("Fund name already exists");
        }
        if (fund.getName() != null ) {
            f.setName(fund.getName());
        }
        if (fund.getCurrency() != null) {
            f.setCurrency(fund.getCurrency());
        }
        return repository.save(f);
    }

    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new NotFoundException("Fund not found: " + id);
        }
        if (transactionRepository.existsByFundId(id)) {
            // block delete and tell the caller what to do
            throw new ConflictException("Cannot delete: fund has transactions. Close/Archive the fund instead.");
        }
        repository.deleteById(id); // allowed only when no txns
    }
}
