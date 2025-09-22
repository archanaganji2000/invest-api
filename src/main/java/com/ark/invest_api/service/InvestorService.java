package com.ark.invest_api.service;
import com.ark.invest_api.dto.Investor;
import com.ark.invest_api.repository.InvestorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestorService {


    @Autowired
    private InvestorRepository repository;
    public Investor add(Investor investor) {
        return repository.save(investor); }

    public List<Investor> all() {

        return repository.findAll();
    }


    public Investor get(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Investor not found with id " + id));
    }

    public Investor update(Long id, Investor investor) {
        // 1. Load the existing  by ID
        Investor i = repository.getReferenceById(id);
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
        repository.deleteById(id);
    }
}
