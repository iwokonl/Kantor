package pl.kantor.backend.MVC.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.kantor.backend.MVC.model.Transaction;
import pl.kantor.backend.MVC.service.TransactionService;

@RestController
@RequestMapping("/transactions")
@Tag(name  = "Transakcje")
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