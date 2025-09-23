package com.ark.invest_api.controller;

import com.ark.invest_api.dto.Fund;
import com.ark.invest_api.dto.Investor;
import com.ark.invest_api.service.InvestorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investors")
public class InvestorController {
    @Autowired
    private InvestorService investorService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Investor> add(@Valid @RequestBody Investor inv) {
        Investor investor = investorService.add(inv);
        return ResponseEntity.status(HttpStatus.CREATED).body(investor);
    }


    @GetMapping("/")
    public List<Investor> list() { return investorService.all(); }

    @GetMapping("/{id}")
    public ResponseEntity<Investor>  get(@PathVariable Long id) {
        Investor investor = investorService.get(id);
        return ResponseEntity.ok(investor);
    }



    @PatchMapping("/{id}")
    public Investor update(@PathVariable Long id, @RequestBody Investor patch) {
        return investorService.update(id, patch);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        investorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

