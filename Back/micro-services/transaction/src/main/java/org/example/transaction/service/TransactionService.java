package org.example.transaction.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.transaction.Feign.UserClient;
import org.example.transaction.dto.AddTransactionDto;
import org.example.transaction.dto.GetTransactionDto;
import org.example.transaction.dto.TransactionDto;
import org.example.transaction.dto.UserDto;
import org.example.transaction.exeption.AppExeption;
import org.example.transaction.model.Transaction;
import org.example.transaction.model.TypeOfTransaction;
import org.example.transaction.repo.TransactionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    @Autowired
    private HttpServletRequest request;
    private UserClient userClient;
    private final TransactionRepo transactionRepo;
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public void addTransaction(AddTransactionDto addTransactionDto) {
        try {
            logger.info("Add transaction");
            // TODO: Ustawić odpowiednie typy Dla dto
            Transaction transaction = new Transaction();
            transaction.setTypeOfTransaction(TypeOfTransaction.valueOf(addTransactionDto.typeOfTransaction()));
            transaction.setAppUserId(Long.parseLong(addTransactionDto.appUserId()));
            transaction.setAmountOfForeginCurrency(new BigDecimal(addTransactionDto.amountOfForeginCurrency()));
            transaction.setTargetCurrency(new BigDecimal(addTransactionDto.targetCurrency()));
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setAmountOfForeginCurrency( new BigDecimal(addTransactionDto.amountOfForeginCurrency()));
            transaction.setExchangeRate(Float.parseFloat(addTransactionDto.exchangeRate()));
            transaction.setForeginCurrencyId(63L);
            transaction.setTargetCurrencyId(addTransactionDto.targetCurrencyId());
            transactionRepo.save(transaction);
        }   catch (Exception e) {
            throw new AppExeption(e.getMessage(), "Transaction", HttpStatus.BAD_REQUEST);
        }

    }
    public String getToken(){
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7); // Odcinamy "Bearer " aby uzyskać sam token
            return jwtToken;
        }
        return null;
    }
    public List<GetTransactionDto> getTransactions(String id) {
        UserDto userDto = userClient.getUserInfo().orElseThrow(() -> new AppExeption("User not found", "Paypal", HttpStatus.NOT_FOUND));

        List<GetTransactionDto> transactions = transactionRepo.findAllByAppUserIdAndForeginCurrencyId(userDto.getId(), Long.valueOf(id));
        return transactions;
    }
}

