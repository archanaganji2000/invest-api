package com.ark.invest_api.controller;


import com.ark.invest_api.dto.Transaction;
import com.ark.invest_api.dto.TransactionRequest;
import com.ark.invest_api.dto.UpdateRequest;
import com.ark.invest_api.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public  class TransactionController {
    @Autowired
    private TransactionService service;

    @GetMapping("/")
    public List<TransactionRequest> list() {
        return service.all();
    }

    @GetMapping("/{id}")
    public TransactionRequest get(@PathVariable Long id) {
        return service.get(id);
    }


    @RequestMapping(value="/save", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionRequest create(@Validated  @RequestBody TransactionRequest req) {

        return service.create(req);
    }

    @PutMapping("/{id}")
    public TransactionRequest update(@PathVariable Long id, @Validated @RequestBody UpdateRequest req) {

        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }

}



