package pl.zeto.backend.VMC.model;

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
    private Long transactionId;
    private Long accountId; // Uproszczony przykład, w rzeczywistości użyj relacji @ManyToOne do Account
    private BigDecimal amount;
    private String transactionType;
    private LocalDateTime timestamp;
}
