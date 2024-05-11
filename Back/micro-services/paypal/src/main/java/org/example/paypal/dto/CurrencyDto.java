package org.example.paypal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CurrencyDto {
    private Long id;
    private String code;
    private String name;
    private Long userId;
}
