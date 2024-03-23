package pl.kantor.backend.MVC.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Currency;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ForeignCurrencyAccountDto {
    private Long id;
    private String curencyCode;
    private String curencyName;
    private String balance;
    private Long userId;


}
