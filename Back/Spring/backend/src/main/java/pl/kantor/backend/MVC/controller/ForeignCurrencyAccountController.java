package pl.kantor.backend.MVC.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.kantor.backend.MVC.dto.ForeignCurrencyAccountDto;
import pl.kantor.backend.MVC.dto.UserIdDto;
import pl.kantor.backend.MVC.model.ForeignCurrencyAccount;
import pl.kantor.backend.MVC.service.ForeignCurrencyAccountService;
import pl.kantor.backend.MVC.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ForeignCurrencyAccount")
public class ForeignCurrencyAccountController {
    private final ForeignCurrencyAccountService foreignCurrencyAccountService;
    private final UserService userService;
    @PostMapping("/getCurrencyAccounts")
    public ResponseEntity<List<ForeignCurrencyAccountDto>> getCurrencyAccounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Map<String,String> response = userService.jwtInfo(currentUserName);
        String userId = response.get("id");
        List<ForeignCurrencyAccountDto> accounts = foreignCurrencyAccountService.getAllAccountsByUserId(Long.parseLong(userId));
        return ResponseEntity.ok(accounts);
    }
}
