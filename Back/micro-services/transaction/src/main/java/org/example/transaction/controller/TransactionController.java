package org.example.transaction.controller;

import lombok.RequiredArgsConstructor;
import org.example.transaction.Feign.UserClient;
import org.example.transaction.dto.AddTransactionDto;
import org.example.transaction.dto.GetTransactionDto;
import org.example.transaction.dto.TransactionDto;
import org.example.transaction.model.Transaction;
import org.example.transaction.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RestController
@RequestMapping("v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final UserClient userClient;
    private final TransactionService transactionService;

    @PostMapping("/addTransaction")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTransaction(@RequestBody AddTransactionDto addTransactionDto) {
        transactionService.addTransaction(addTransactionDto);
    }

    @GetMapping("/getTransactions/{id}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String id) {
        return ResponseEntity.ok(transactionService.getTransactions(id,userClient.getUserInfo()));
    }

    @GetMapping("/getTransactionByUser")
    public ResponseEntity<List<Transaction>> getTransactionByUser() {
        return ResponseEntity.ok(transactionService.getTransactionByUser());
    }
}
