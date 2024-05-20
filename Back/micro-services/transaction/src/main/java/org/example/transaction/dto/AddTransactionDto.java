package org.example.transaction.dto;

public record AddTransactionDto(String appUserId,Long ForeginCurrencyId,Long targetCurrencyId, String amountOfForeginCurrency, String targetCurrency, String typeOfTransaction, String exchangeRate) {
}
