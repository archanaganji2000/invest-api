package com.ark.invest_api.dto;

import java.util.Map;
import java.util.UUID;

public record InvestorSummary(
    Long investorId,
    String investorName,
    Map<Long, InvestorFundHolding> holdings
){}
