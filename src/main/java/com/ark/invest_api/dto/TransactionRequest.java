package com.ark.invest_api.dto;


import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionRequest {
    private Long fundId;
    private Long investorId;
    private LocalDate date;
    private BigDecimal amount;
    private TransactionType type;

    // getters/setters
    public Long getFundId() { return fundId; }
    public void setFundId(Long fundId) { this.fundId = fundId; }

    public Long getInvestorId() { return investorId; }
    public void setInvestorId(Long investorId) { this.investorId = investorId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

}
