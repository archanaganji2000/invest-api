package com.ark.invest_api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record InvestorFundHolding(
        Long fundId,
        String fundName,
        BigDecimal contributed,
        BigDecimal distributed,
        BigDecimal netCashFlow,
        BigDecimal currentBalanceShare
) {}
