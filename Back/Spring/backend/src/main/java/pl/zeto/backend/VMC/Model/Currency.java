package pl.zeto.backend.VMC.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private String code; // Kod waluty, np. "USD"
    private String name; // Nazwa waluty, np. "dolar amerykański"
    private Double exchangeRate; // Kurs wymiany względem waluty bazowej, np. PLN

    // Konstruktory, gettery i settery
    public Currency() {
    }

    public Currency(String code, String name, Double exchangeRate) {
        this.code = code;
        this.name = name;
        this.exchangeRate = exchangeRate;
    }

    // Gettery i settery

}
