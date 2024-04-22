package org.example.transaction.service;

import lombok.RequiredArgsConstructor;
import org.example.transaction.dto.AddTransactionDto;
import org.example.transaction.dto.TransactionDto;
import org.example.transaction.exeption.AppExeption;
import org.example.transaction.model.Transaction;
import org.example.transaction.repo.TransactionRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<TransactionDto> getTransactions(String id) {
        List<Transaction> transactions = transactionRepo.findAllById(Long.parseLong(id));
        List<TransactionDto> transactionDtos = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionDtos.add(new TransactionDto(
                    transaction.getId().toString(),
                    transaction.getAppUserId().toString(),
                    transaction.getAmount().toString(),
                    transaction.getCurrencyFromAccountId().toString(),
                    transaction.getCurrencyToAccountId().toString(),
                    transaction.getTransactionDate().toString()
            ));
        }
        return transactionDtos;
    }
}

