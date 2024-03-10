package pl.zeto.backend.VMC.model;

import jakarta.persistence.*;

@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 3)
    private String code; // Kod waluty, np. "USD"

    @Column(nullable = false)
    private String name; // Pełna nazwa waluty, np. "United States Dollar"

    // Konstruktory, gettery, settery
}
