package org.example.transaction.controller;

import lombok.RequiredArgsConstructor;
import org.example.transaction.dto.AddTransactionDto;
import org.example.transaction.dto.GetTransactionDto;
import org.example.transaction.dto.TransactionDto;
import org.example.transaction.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/addTransaction")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTransaction(@RequestBody AddTransactionDto addTransactionDto) {
        transactionService.addTransaction(addTransactionDto);
    }

    @GetMapping("/getTransactions")
    public ResponseEntity<List<GetTransactionDto>> getTransactions(@RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.getTransactions(transactionDto.id()));
    }
}
