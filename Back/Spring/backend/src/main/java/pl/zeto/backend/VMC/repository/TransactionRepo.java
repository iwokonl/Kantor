package pl.zeto.backend.VMC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zeto.backend.VMC.model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
