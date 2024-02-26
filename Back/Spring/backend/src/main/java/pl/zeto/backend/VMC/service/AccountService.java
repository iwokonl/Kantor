package pl.zeto.backend.VMC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zeto.backend.VMC.model.AppAccount;
import pl.zeto.backend.VMC.repository.AccountRepo;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepository;

    public AppAccount addAccount(AppAccount account) {
        return accountRepository.save(account);
    }
}
