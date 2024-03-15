package pl.zeto.backend.VMC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zeto.backend.VMC.model.ForeignCurrencyAccount;
import pl.zeto.backend.VMC.model.Transaction;
import pl.zeto.backend.VMC.repository.AccountRepo;
import pl.zeto.backend.VMC.repository.TransactionRepo;

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
                .orElseThrow(() -> new RuntimeException("Konto o ID " + accountId + " nie zostało znalezione."));

        if(account != null) {
            // Tutaj możesz dodać logikę aktualizacji salda konta, jeśli jest to wymagane
            // Na przykład, możesz chcieć zwiększyć lub zmniejszyć saldo w zależności od typu transakcji
            // account.setBalance(account.getBalance().add(transaction.getAmount())); // Dla przykładu
            accountRepository.save(account); // Zapisz aktualizacje konta
        } else {
            // Jeśli transakcja nie ma przypisanego konta, rzucamy wyjątek lub obsługujemy błąd
            throw new RuntimeException("Transakcja nie jest przypisana do żadnego konta.");
        }

        return transactionRepository.save(transaction);
    }

    public Transaction getTransaction(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transakcja o ID " + id + " nie została znaleziona."));
    }
}