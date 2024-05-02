package org.example.currencyaccounts.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ForeignCurrencyAccountDto {
    private Long id;
    private String curencyCode; //!TODO zmienić na currencyCode
    private String curencyName; //!TODO zmienić na currencyName
    private String balance;
    private Long userId;


}
