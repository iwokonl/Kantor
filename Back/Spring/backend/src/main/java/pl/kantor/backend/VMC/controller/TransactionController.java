package pl.kantor.backend.VMC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.kantor.backend.VMC.model.Transaction;
import pl.kantor.backend.VMC.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/addTransaction/{accountId}")
    public Transaction addTransaction(@RequestBody Transaction transaction, @PathVariable Long accountId) {
        return transactionService.addTransaction(transaction, accountId);
    }

    @GetMapping("/getTransaction/{id}")
    public Transaction getTransaction(@PathVariable Long accountId) {
        return transactionService.getTransaction(accountId);
    }
}