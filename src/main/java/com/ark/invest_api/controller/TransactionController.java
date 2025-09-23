package com.ark.invest_api.controller;


import com.ark.invest_api.dto.TransactionRequest;
import com.ark.invest_api.dto.UpdateRequest;
import com.ark.invest_api.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public  class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<TransactionRequest>> list() {
        return ResponseEntity.ok(service.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionRequest> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> create(@Validated  @RequestBody TransactionRequest req) {
        service.create(req);
        URI location = URI.create("/api/investors/save/"+ req.getId());
        return ResponseEntity.created(location).body(Map.of("message", "Transaction added successfully"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }

    @PutMapping("/{id}")
    public TransactionRequest update(@PathVariable Long id, @Validated @RequestBody UpdateRequest req) {

        return service.update(id, req);
    }

}



