package com.ark.invest_api.controller;

import com.ark.invest_api.dto.Fund;
import com.ark.invest_api.service.FundsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/funds")
public class FundsController {

    private final FundsService fundsService;

    public FundsController(FundsService fundsService) {
        this.fundsService = fundsService;
    }

    @PostMapping(path = "/save")
    public ResponseEntity<Map<String, String>> save(@Validated @RequestBody Fund fund) {
        Fund savedFund = fundsService.addFunds(fund);
        URI location = URI.create("/api/funds/save/"+ savedFund.getId());
        return ResponseEntity.created(location).body(Map.of("message", "Fund created successfully"));
    }

    @GetMapping("/")
    public ResponseEntity<List<Fund>> get() {
        return ResponseEntity.ok(fundsService.getAllFunds());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Fund> get(@PathVariable Long id) {
        return ResponseEntity.ok(fundsService.get(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Fund> update(@PathVariable Long id, @RequestBody Fund fund) {
        Fund updatedFund = fundsService.update(id, fund);
        return ResponseEntity.ok(updatedFund);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id)  {
        fundsService.delete(id);
        return ResponseEntity.ok().body(Map.of("message", "Fund deleted successfully"));
    }

}
