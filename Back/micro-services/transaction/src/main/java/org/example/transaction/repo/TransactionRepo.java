package org.example.transaction.repo;

import org.example.transaction.dto.GetTransactionDto;
import org.example.transaction.dto.TransactionDto;
import org.example.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAppUserIdAndTargetCurrencyId(Long appUserId, Long targetCurrencyId);
}
