package org.example.paypal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
public class AddTransactionDto {
    private String appUserId;
    private Long ForeginCurrencyId;
    private Long targetCurrencyId;
    private String amountOfForeginCurrency;
    private String targetCurrency;
    private String typeOfTransaction;
    String exchangeRate;
}

