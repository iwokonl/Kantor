package pl.kantor.backend.MVC.dto;

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
