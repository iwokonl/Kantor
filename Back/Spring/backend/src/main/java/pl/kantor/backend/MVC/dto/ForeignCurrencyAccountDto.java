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
    private String curencyCode; //!TODO zmienić na currencyCode
    private String curencyName; //!TODO zmienić na currencyName
    private String balance;
    private Long userId;


}
