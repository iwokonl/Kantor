package pl.kantor.backend.VMC.dto;

import jakarta.persistence.Column;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CurrencyDto {
    private Long id;
    private String code;
    private String name;
}
