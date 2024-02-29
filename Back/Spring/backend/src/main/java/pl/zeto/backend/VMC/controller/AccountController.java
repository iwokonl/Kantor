package pl.zeto.backend.VMC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.zeto.backend.VMC.model.AppAccount;
import pl.zeto.backend.VMC.model.AppUser;
import pl.zeto.backend.VMC.service.AccountService;
import org.springframework.ui.Model;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/addAccount")
    public String addAccount(Model model) {
        model.addAttribute("account", new AppAccount());
        return "adding new account";
    }

    @GetMapping("/getAccount/{id}")
    public AppAccount getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }


}
