package pl.kantor.backend.MVC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.kantor.backend.MVC.model.Transaction;
import pl.kantor.backend.MVC.repository.AccountRepo;
import pl.kantor.backend.MVC.exeption.AppExeption;
import pl.kantor.backend.MVC.model.ForeignCurrencyAccount;
import pl.kantor.backend.MVC.repository.TransactionRepo;

import java.util.Set;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepo transactionRepository;

    @Autowired
    private AccountRepo accountRepository;

    public Transaction addTransaction(Transaction transaction, Long accountId) {
        Set<ForeignCurrencyAccount> accounts = transaction.getUser().getAccounts(); // Odniesienie do wielu kont

        ForeignCurrencyAccount account = accounts.stream()
                .filter(acc -> acc.getId().equals(accountId))
                .findFirst()
                .orElseThrow(() -> new AppExeption("Konto o ID " + accountId + " nie zostało znalezione.", HttpStatus.NOT_FOUND));

        if(account != null) {
            // Tutaj możesz dodać logikę aktualizacji salda konta, jeśli jest to wymagane
            // Na przykład, możesz chcieć zwiększyć lub zmniejszyć saldo w zależności od typu transakcji
            // account.setBalance(account.getBalance().add(transaction.getAmount())); // Dla przykładu
            accountRepository.save(account); // Zapisz aktualizacje konta
        } else {
            // Jeśli transakcja nie ma przypisanego konta, rzucamy wyjątek lub obsługujemy błąd
            throw new AppExeption("Transakcja nie jest przypisana do żadnego konta.", HttpStatus.BAD_REQUEST);
        }

        return transactionRepository.save(transaction);
    }

    public Transaction getTransaction(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new AppExeption("Transakcja o ID " + id + " nie została znaleziona.", HttpStatus.NOT_FOUND));
    }
}