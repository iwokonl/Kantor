package pl.zeto.backend.VMC.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Data
public class CurrencyResponse {
    private String currency;
    private List<Rate> rates;

    @Getter
    @Setter
    public static class Rate {
        private Double mid;
    }
}
