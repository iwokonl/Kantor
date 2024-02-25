package pl.zeto.backend.VMC.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private Double rate;

    public Currency() {
    }

    public Currency(String code, String name, Double rate) {
        this.code = code;
        this.name = name;
        this.rate = rate;
    }
}