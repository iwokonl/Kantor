package pl.kantor.backend.MVC.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kantor.backend.MVC.dto.ForeignCurrencyAccountDto;
import pl.kantor.backend.MVC.dto.UserIdDto;
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
    @PostMapping("/getCurrencyAccounts")
    public ResponseEntity<List<ForeignCurrencyAccountDto>> getCurrencyAccounts(@RequestBody UserIdDto payload) {
        List<ForeignCurrencyAccountDto> accounts = foreignCurrencyAccountService.getAllAccountsByUserId(payload.userId());
        return ResponseEntity.ok(accounts);
    }
}
