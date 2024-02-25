package pl.zeto.backend.VMC.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyData {
    private String code; // Kod waluty, np. "USD"
    private String name; // Pełna nazwa waluty, np. "Dolar amerykański"
    private Double exchangeRate; // Kurs wymiany
}