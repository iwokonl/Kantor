package org.example.currencyaccounts.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForeignCurrencyAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;
    private Long currencyId; // Dodajemy pole do przechowywania waluty konta

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Long userId;
    // Konstruktor, gettery, settery
}