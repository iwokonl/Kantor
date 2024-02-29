package pl.zeto.backend.VMC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zeto.backend.VMC.model.AppAccount;
import pl.zeto.backend.VMC.repository.AccountRepo;

import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepository;

    public AppAccount addAccount(AppAccount account) {
        account.setBalance(new BigDecimal(0));

        return accountRepository.save(account);
    }

    public AppAccount getAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }


}
