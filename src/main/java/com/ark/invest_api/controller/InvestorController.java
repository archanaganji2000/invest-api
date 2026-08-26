package com.ark.invest_api.controller;


import com.ark.invest_api.dto.Investor;
import com.ark.invest_api.service.InvestorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/investors")
public class InvestorController {
    @Autowired
    private InvestorService investorService;

    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> add(@Valid @RequestBody Investor inv) {
        Investor investor = investorService.add(inv);
        URI location = URI.create("/api/investors/save/"+ investor.getId());
        return ResponseEntity.created(location).body(Map.of("message", "Investor added successfully"));
    }

    @GetMapping("/")
    public ResponseEntity<List<Investor>> list() {
        return ResponseEntity.ok(investorService.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Investor>  get(@PathVariable Long id) {
        return ResponseEntity.ok(investorService.get(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Investor> update(@PathVariable Long id, @RequestBody Investor patch) {
        Investor updatedInvestor = investorService.update(id, patch);
        return ResponseEntity.ok(updatedInvestor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        investorService.delete(id);
        return ResponseEntity.ok().body(Map.of("message", "Investor deleted successfully"));
    }
}

