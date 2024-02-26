package pl.zeto.backend.VMC.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zeto.backend.VMC.model.AppAccount;
import pl.zeto.backend.VMC.model.Transaction;
import pl.zeto.backend.VMC.repository.AccountRepo;
import pl.zeto.backend.VMC.repository.TransactionRepo;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepo transactionRepository;

    @Autowired
    private AccountRepo accountRepository;

    public Transaction addTransaction(Transaction transaction) {
        // Opcjonalnie: aktualizacja salda konta w zależności od typu transakcji
        Optional<AppAccount> account = accountRepository.findById(transaction.getAccountId());
        if(account.isPresent()) {
            // Logika aktualizacji salda
        }
        return transactionRepository.save(transaction);
    }
}
