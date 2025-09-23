package com.ark.invest_api.service;

import com.ark.invest_api.dto.Fund;
import com.ark.invest_api.exceptions.ConflictException;
import com.ark.invest_api.exceptions.InvalidArgumentException;
import com.ark.invest_api.exceptions.NotFoundException;
import com.ark.invest_api.repository.FundRepository;
import com.ark.invest_api.repository.InvestorRepository;
import com.ark.invest_api.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FundsService {
    private final FundRepository repository;

    private final TransactionRepository transactionRepository;
    private final InvestorRepository investorRepository;

    public FundsService(FundRepository repository, TransactionRepository transactionRepository, InvestorRepository investorRepository) {
        this.repository = repository;
        this.transactionRepository = transactionRepository;
        this.investorRepository = investorRepository;
    }

    public Fund addFunds(Fund fund) {

        if (fund.getName() == null || fund.getName().isBlank()) {
            throw new InvalidArgumentException("name is required");
        }
        String name = fund.getName().trim();
        fund.setName(name);

        String currency = (fund.getCurrency() == null || fund.getCurrency().isBlank()) ? "USD" : fund.getCurrency().trim().toUpperCase();
        currencyValidation(currency);
        fund.setCurrency(currency);

        if (repository.existsByNameIgnoreCase(name)) {
            throw new ConflictException("Fund with name '" + name + "' already exists.");
        }

        return repository.save(fund);
    }

    public List<Fund> getAllFunds() {
        return repository.findAll();
    }

    public Fund get(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Fund not found with id " + id));
    }

    public Fund update(Long id, Fund fund) {

        boolean equalName, equalCurrency;
        Fund existingFund = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fund not found: " + id));
        if (fund.getName() == null && fund.getCurrency() == null)
            throw new InvalidArgumentException("At least one of name or currency must be provided for update.");
        equalName = existingFund.getName().equals(fund.getName());
        equalCurrency = existingFund.getCurrency().equals(fund.getCurrency());
        if (equalName && equalCurrency)
            throw new InvalidArgumentException("At least one of name or currency must be different from existing values.");

        if((fund.getName()!= null && fund.getName().isBlank()) || (fund.getCurrency()!= null && fund.getCurrency().isBlank()))
            throw new InvalidArgumentException("Name or Currency cannot be blank.");

        if (equalName && !equalCurrency && fund.getCurrency() != null) {
            currencyValidation(fund.getCurrency().trim().toUpperCase());
            existingFund.setCurrency(fund.getCurrency());
        } else if (equalCurrency && !equalName && fund.getName() != null) {
            String trimmedName = fund.getName().trim();
            if (repository.existsByNameIgnoreCase(trimmedName)) {
                throw new ConflictException("Fund with name '" + trimmedName + "' already exists.");
            }
            existingFund.setName(trimmedName);
        }
        if (!equalCurrency && !equalName && repository.existsByNameIgnoreCase(fund.getName()))
            throw new ConflictException("Fund with name '" + fund.getName() + "' already exists.");

        return repository.save(existingFund);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Fund not found: " + id);
        }
        if (transactionRepository.existsByFundId(id)) {
            throw new ConflictException("Cannot delete: fund has transactions. Close/Archive the fund instead.");
        }
    }

    private static void currencyValidation(String currency) {
        if (currency.length() != 3) throw new InvalidArgumentException("currency must be 3 letters (e.g., USD)");
    }

}
