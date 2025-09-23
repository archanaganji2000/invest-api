package com.ark.invest_api.dto;

import java.math.BigDecimal;

public record FundSummary(
        Long fundId,
        String fundName,
        BigDecimal totalContributions,
        BigDecimal totalInterest,
        BigDecimal totalDistributions,
        BigDecimal totalExpenses,
        BigDecimal totalMgmtFees,
        BigDecimal netCashFlow,
        BigDecimal currentBalance
){}
