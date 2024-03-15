package pl.zeto.backend.VMC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zeto.backend.VMC.model.ForeignCurrencyAccount;
import pl.zeto.backend.VMC.repository.AccountRepo;

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
