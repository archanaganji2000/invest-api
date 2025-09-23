package com.ark.invest_api.controller;

import com.ark.invest_api.dto.Fund;
import com.ark.invest_api.service.FundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/funds")
public class FundsController {
    @Autowired
    private FundsService fundsService;

    @PostMapping(path = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Fund> save(@Validated @RequestBody Fund fund) {
        Fund created = fundsService.addfunds(fund);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/")
    public List<Fund> get() {
        return fundsService.getAllFunds();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Fund> get(@PathVariable Long id) {

            Fund fund = fundsService.get(id);
            return ResponseEntity.ok(fund);

    }


    @PatchMapping("/{id}")
    public Fund update(@PathVariable Long id, @RequestBody Fund fund) {
        return fundsService.update(id, fund);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id)  {
       fundsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
