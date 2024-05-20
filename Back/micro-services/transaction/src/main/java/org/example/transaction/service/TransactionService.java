package org.example.transaction.service;

import lombok.RequiredArgsConstructor;
import org.example.transaction.dto.AddTransactionDto;
import org.example.transaction.dto.TransactionDto;
import org.example.transaction.exeption.AppExeption;
import org.example.transaction.model.Transaction;
import org.example.transaction.model.TypeOfTransaction;
import org.example.transaction.repo.TransactionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public void addTransaction(AddTransactionDto addTransactionDto) {
        try {
            logger.info("Add transaction");
            // TODO: Ustawić odpowiednie typy Dla dto
            Transaction transaction = new Transaction();
            transaction.setTypeOfTransaction(TypeOfTransaction.valueOf(addTransactionDto.typeOfTransaction()));
            transaction.setAppUserId(Long.parseLong(addTransactionDto.appUserId()));
            transaction.setAmountOfForeignCurrency(new BigDecimal(addTransactionDto.amountOfForeginCurrency()));
            transaction.setTargetCurrency(new BigDecimal(addTransactionDto.targetCurrency()));
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setAmountOfForeignCurrency( new BigDecimal(addTransactionDto.amountOfForeginCurrency()));
            transaction.setExchangeRate(Float.parseFloat(addTransactionDto.exchangeRate()));
            transaction.setFreignCurrencyId(63L);
            transaction.setTargetCurrencyId(addTransactionDto.targetCurrencyId());
            transactionRepo.save(transaction);
        }   catch (Exception e) {
            throw new AppExeption(e.getMessage(), "Transaction", HttpStatus.BAD_REQUEST);
        }

    }

    public List<TransactionDto> getTransactions(String id) {
        List<Transaction> transactions = transactionRepo.findAllById(Long.parseLong(id));
        List<TransactionDto> transactionDtos = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionDtos.add(new TransactionDto(
                    transaction.getId().toString(),
                    transaction.getAppUserId().toString(),
                    transaction.getAmountOfForeignCurrency().toString(),
                    transaction.getTargetCurrency().toString(),
                    transaction.getTypeOfTransaction().name(),
                    transaction.getTransactionDate().toString()
            ));
        }
        return transactionDtos;
    }
}

