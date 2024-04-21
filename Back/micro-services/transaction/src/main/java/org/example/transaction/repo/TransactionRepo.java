package org.example.transaction.repo;

import org.example.transaction.Dto.TransactionDto;
import org.example.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<TransactionDto> findAllById(Long accountId);
}
