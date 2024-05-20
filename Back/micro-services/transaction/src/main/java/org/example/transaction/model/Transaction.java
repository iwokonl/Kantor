package org.example.transaction.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long appUserId;
    private Long foreginCurrencyId;
    private Long targetCurrencyId;
    private BigDecimal amountOfForeginCurrency;
    private BigDecimal targetCurrency;
    private Float exchangeRate;
    @Enumerated(EnumType.STRING)
    private TypeOfTransaction typeOfTransaction;
    private LocalDateTime transactionDate;
}
