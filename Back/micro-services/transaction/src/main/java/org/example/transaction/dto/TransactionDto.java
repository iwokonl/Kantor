package org.example.transaction.dto;

public record TransactionDto(String id, String appUserId, String amount, String currencyFromAccountId,
                             String currencyToAccountId, String transactionDate) {
}
