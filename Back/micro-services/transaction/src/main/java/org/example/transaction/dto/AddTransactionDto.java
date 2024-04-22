package org.example.transaction.dto;

public record AddTransactionDto(String appUserId, String amount, String currencyFromAccountId, String currencyToAccountId) {
}
