package com.ark.invest_api.controller;


import com.ark.invest_api.dto.Transaction;
import com.ark.invest_api.dto.TransactionRequest;
import com.ark.invest_api.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public  class TransactionController {
    @Autowired
    private TransactionService service;

    @GetMapping("/")
    public List<Transaction> list() {
        System.out.println("hiii");
        return service.all();
    }

    @GetMapping("/{id}")
    public Transaction get(@PathVariable Long id) {
        return service.get(id);
    }


    @PostMapping
    @RequestMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction create(@Validated @RequestBody TransactionRequest req) {

        return service.create(req);
    }
}



