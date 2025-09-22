package com.ark.invest_api.service;

import com.ark.invest_api.dto.Fund;
import com.ark.invest_api.repository.FundRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FundsService {
    @Autowired
    private  FundRepository repository;
    public void addfunds(Fund fund) {
         repository.save(fund); }

    public List<Fund> getAllFunds() {

        List<Fund> funds=  repository.findAll();
        System.out.println(funds);
        return funds;
    }


    public Fund get(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Fund not found with id " + id));
    }

    public Fund update(Long id, Fund fund) {
        // 1. Load the existing fund by ID
        Fund f = repository.getReferenceById(id);
        if (fund.getName() != null) {
            f.setName(fund.getName());
        }
        if (fund.getCurrency() != null) {
            f.setCurrency(fund.getCurrency());
        }
        return repository.save(f);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
