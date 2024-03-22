package pl.kantor.backend.MVC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kantor.backend.MVC.repository.AccountRepo;
import pl.kantor.backend.MVC.model.ForeignCurrencyAccount;

import java.math.BigDecimal;

@Service
public class ForeignCurrencyAccountService {

    @Autowired
    private AccountRepo accountRepository;

    public ForeignCurrencyAccount addAccount(ForeignCurrencyAccount account) {
        account.setBalance(new BigDecimal(0));

        return accountRepository.save(account);
    }

    public ForeignCurrencyAccount getAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }


}
