package com.ark.invest_api.dto;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name ="Transaction")
public class TransactionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long fundId;

    @Column(nullable = false)
    private Long investorId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "id=" + id +
                ", fundId=" + fundId +
                ", investorId=" + investorId +
                ", date=" + date +
                ", amount=" + amount +
                ", type=" + type +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(Long fundId) {
        this.fundId = fundId;
    }

    public Long getInvestorId() {
        return investorId;
    }

    public void setInvestorId(Long investorId) {
        this.investorId = investorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
