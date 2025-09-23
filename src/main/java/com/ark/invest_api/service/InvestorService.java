package com.ark.invest_api.service;

import com.ark.invest_api.dto.Investor;
import com.ark.invest_api.exceptions.ConflictException;
import com.ark.invest_api.exceptions.InvalidArgumentException;
import com.ark.invest_api.exceptions.NotFoundException;
import com.ark.invest_api.repository.InvestorRepository;
import com.ark.invest_api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestorService {


    @Autowired
    private InvestorRepository repository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Investor add(Investor investor) {
        if(investor.getEmail() == null){
            throw new InvalidArgumentException("Email is required");
        }
        if(investor.getFirstName() == null && investor.getLastName()==null)
        {
            throw new InvalidArgumentException("Name is required");
        }
        return repository.save(investor);
    }

    public List<Investor> all() {

        return repository.findAll();
    }


    public Investor get(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Investor not found with id " + id));
    }

    public Investor update(Long id, Investor investor) {
        // 1. Load the existing  by ID
        Investor i = repository.findById(investor.getId()).orElseThrow(() -> new NotFoundException("investor not found: " + id));

        if (investor.getEmail() != null) {
            i.setEmail(investor.getEmail());
        }
        if (investor.getFirstName() != null) {
            i.setFirstName(investor.getFirstName());
        }
        if (investor.getLastName() != null) {
            i.setLastName(investor.getLastName());
        }

        return repository.save(i);
    }

    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new NotFoundException("investor not found: " + id);
        }
        if (transactionRepository.existsByInvestorId(id)) {
            // block delete and tell the caller what to do
            throw new ConflictException("Cannot delete: investor has transactions. Close/Archive the investor instead.");
        }
        repository.deleteById(id); // allowed only when no txns
    }
}
