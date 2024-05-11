package org.example.paypal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ForeignCurrencyAccountDto {
    private Long id;
    private String currencyId;
    private BigDecimal balance;
    private Long userId;


}
