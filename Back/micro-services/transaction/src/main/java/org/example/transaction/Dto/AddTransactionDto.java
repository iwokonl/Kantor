package org.example.transaction.Dto;

public record AddTransactionDto(String appUserId, String amount, String currencyFromAccountId, String currencyToAccountId) {
}
