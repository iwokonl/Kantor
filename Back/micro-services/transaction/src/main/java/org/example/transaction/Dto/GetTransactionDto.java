package org.example.transaction.Dto;

public record GetTransactionDto(String appUserId, String currencyFromAccountId, String currencyToAccountId) {
}
