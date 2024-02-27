package pl.zeto.backend.VMC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.zeto.backend.VMC.model.AppAccount;
import pl.zeto.backend.VMC.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/addAccount")
    public AppAccount addAccount(@RequestBody AppAccount account) {
        return accountService.addAccount(account);
    }

    @GetMapping("/getAccount/{id}")
    public AppAccount getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }
}
