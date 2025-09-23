package com.ark.invest_api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateRequest(
    @NotNull LocalDate date,
    @NotNull BigDecimal amount,
    @NotNull TransactionType type
){}