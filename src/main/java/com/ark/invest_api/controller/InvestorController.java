package com.ark.invest_api.controller;

import com.ark.invest_api.dto.Investor;
import com.ark.invest_api.service.InvestorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investors")
public class InvestorController {
    @Autowired
    private InvestorService investorService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Investor add(@Valid @RequestBody Investor investor) {
        return investorService.add(investor);
    }
    @GetMapping("/")
    public List<Investor> list() { return investorService.all(); }

    @GetMapping("/{id}")
    public Investor get(@PathVariable Long id) { return investorService.get(id); }



    @PatchMapping("/{id}")
    public Investor update(@PathVariable Long id, @RequestBody Investor patch) {
        return investorService.update(id, patch);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { investorService.delete(id); }
}

