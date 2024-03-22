package pl.kantor.backend.MVC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kantor.backend.MVC.model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
