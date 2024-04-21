package org.example.transaction.Dto;

public record TransactionDto(String id, String appUserId, String amount, String currencyFromAccountId, String currencyToAccountId, String transactionDate) {
}
