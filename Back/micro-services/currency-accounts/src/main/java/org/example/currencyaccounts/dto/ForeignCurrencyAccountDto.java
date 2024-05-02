package org.example.currencyaccounts.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ForeignCurrencyAccountDto {
    private Long id;
    private String currencyId;
    private String balance;
    private Long userId;


}
