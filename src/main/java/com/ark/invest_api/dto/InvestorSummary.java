package com.ark.invest_api.dto;

import java.util.Map;


public record InvestorSummary(
    Long investorId,
    String investorName,
    Map<Long, InvestorFundHolding> holdings
){}
