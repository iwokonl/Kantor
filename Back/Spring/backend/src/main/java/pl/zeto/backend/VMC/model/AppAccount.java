package pl.zeto.backend.VMC.model;

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
public class AppAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private Long userId; // Uproszczony przykład, w rzeczywistości użyj relacji @ManyToOne do User
    private String currency;
    private BigDecimal balance;
}
