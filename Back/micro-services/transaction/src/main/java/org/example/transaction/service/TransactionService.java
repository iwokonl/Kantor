package org.example.transaction.service;

import lombok.RequiredArgsConstructor;
import org.example.transaction.Dto.AddTransactionDto;
import org.example.transaction.Dto.TransactionDto;
import org.example.transaction.model.Transaction;
import org.example.transaction.repo.TransactionRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepo transactionRepo;

    public void addTransaction(AddTransactionDto addTransactionDto) {
        Transaction transaction = Transaction.builder()
                .appUserId(Long.parseLong(addTransactionDto.appUserId()))
                .amount(new BigDecimal(addTransactionDto.amount()))
                .currencyFromAccountId(Long.parseLong(addTransactionDto.currencyFromAccountId()))
                .currencyToAccountId(Long.parseLong(addTransactionDto.currencyToAccountId()))
                .transactionDate(LocalDateTime.now())
                .build();
        transactionRepo.save(transaction);
    }

    public List<TransactionDto> getTransactions(Long accountId) {
        return transactionRepo.findAllById(accountId);
    }
}

