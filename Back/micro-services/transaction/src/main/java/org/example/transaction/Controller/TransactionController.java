package org.example.transaction.Controller;

import lombok.RequiredArgsConstructor;
import org.example.transaction.Dto.AddTransactionDto;
import org.example.transaction.Dto.TransactionDto;
import org.example.transaction.model.Transaction;
import org.example.transaction.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/addTransaction")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTransaction(@RequestBody AddTransactionDto addTransactionDto) {
        transactionService.addTransaction(addTransactionDto);
    }

    @GetMapping("/getTransactions")
    public ResponseEntity<List<TransactionDto>> getTransactions(@RequestParam Long accountId) {
        return ResponseEntity.ok(transactionService.getTransactions(accountId));
    }
}
