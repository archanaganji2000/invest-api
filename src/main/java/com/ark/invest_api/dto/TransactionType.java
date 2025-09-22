package com.ark.invest_api.dto;

public enum TransactionType {
    CONTRIBUTION,        // Credit
    INTEREST_INCOME,     // Credit
    DISTRIBUTION,        // Debit
    GENERAL_EXPENSE,     // Debit
    MANAGEMENT_FEE       // Debit
}
