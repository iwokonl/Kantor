package org.example.transaction.dto;

public record GetTransactionDto(String appUserId, String currencyFromAccountId, String currencyToAccountId) {
}
