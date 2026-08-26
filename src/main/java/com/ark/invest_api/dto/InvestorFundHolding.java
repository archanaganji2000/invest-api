package com.ark.invest_api.dto;

import java.math.BigDecimal;

public record InvestorFundHolding(
        Long fundId,
        String fundName,
        BigDecimal contributed,
        BigDecimal distributed,
        BigDecimal netCashFlow,
        BigDecimal currentBalanceShare
) {}
