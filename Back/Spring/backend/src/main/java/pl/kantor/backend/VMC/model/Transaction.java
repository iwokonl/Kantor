package pl.kantor.backend.VMC.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    private BigDecimal amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_from_foreign_furrency_account_id", referencedColumnName = "id")
    private ForeignCurrencyAccount currencyFromAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_to_foreign_currency_account_id", referencedColumnName = "id")
    private ForeignCurrencyAccount currencyToAccount;
    private LocalDateTime transactionDate;

    // Konstruktor, gettery, settery
}