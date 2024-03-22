package pl.kantor.backend.VMC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kantor.backend.VMC.model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
