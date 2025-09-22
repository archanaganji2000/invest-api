package com.ark.invest_api.controller;

import com.ark.invest_api.dto.Fund;
import com.ark.invest_api.service.FundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public void save(@Validated @RequestBody Fund fund) {
        fundsService.addfunds(fund);

    }

    @GetMapping("/")
    public List<Fund> get() {
        return fundsService.getAllFunds();
    }

    @GetMapping("/{id}")
    public Fund get(@PathVariable Long id) { return fundsService.get(id); }

    @PatchMapping("/{id}")
    public Fund update(@PathVariable Long id, @RequestBody Fund fund) {
        return fundsService.update(id, fund);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {  fundsService.delete(id);
    }

}
