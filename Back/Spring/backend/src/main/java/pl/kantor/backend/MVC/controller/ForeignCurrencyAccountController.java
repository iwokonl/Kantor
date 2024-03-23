package pl.kantor.backend.MVC.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kantor.backend.MVC.dto.ForeignCurrencyAccountDto;
import pl.kantor.backend.MVC.model.ForeignCurrencyAccount;
import pl.kantor.backend.MVC.service.ForeignCurrencyAccountService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ForeignCurrencyAccount")
public class ForeignCurrencyAccountController {
    private final ForeignCurrencyAccountService foreignCurrencyAccountService;

    //TODO: Zapytać się czy robić w taki sposób @RequestBody Map<String, Long> payload) i co jest bardziej optymalne
    @PostMapping("/getCurrencyAccounts")
    public ResponseEntity<List<ForeignCurrencyAccountDto>> getCurrencyAccounts(@RequestBody Map<String, Long> payload) {
        Long userId = payload.get("userId");
        List<ForeignCurrencyAccountDto> accounts = foreignCurrencyAccountService.getAllAccountsByUserId(userId);
        return ResponseEntity.ok(accounts);
    }
}
