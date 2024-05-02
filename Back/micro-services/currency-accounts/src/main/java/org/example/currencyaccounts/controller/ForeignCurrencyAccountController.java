package org.example.currencyaccounts.controller;

import lombok.RequiredArgsConstructor;
import org.example.currencyaccounts.dto.ForeignCurrencyAccountDto;
import org.example.currencyaccounts.service.ForeignCurrencyAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/ForeignCurrencyAccount")
public class ForeignCurrencyAccountController {
    private final ForeignCurrencyAccountService foreignCurrencyAccountService;

    @PostMapping("/getCurrencyAccounts")
    public ResponseEntity<List<ForeignCurrencyAccountDto>> getCurrencyAccounts() {
        List<ForeignCurrencyAccountDto> accounts = foreignCurrencyAccountService.getAllAccountsByUserId();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/createCurrencyAccount")
    public ResponseEntity<ForeignCurrencyAccountDto> createCurrencyAccount(
            @RequestBody ForeignCurrencyAccountDto foreignCurrencyAccountDto
    ) {
        ForeignCurrencyAccountDto foreignCurrencyAccountDtoToSend = foreignCurrencyAccountService.createForeignCurrencyAccount(
                foreignCurrencyAccountDto.getCurencyCode(),
                foreignCurrencyAccountDto.getBalance());
        return ResponseEntity.ok(foreignCurrencyAccountDtoToSend);
    }

    @DeleteMapping("/deleteCurrencyAccount")
    public ResponseEntity<String> deleteCurrencyAccount(
            @RequestBody ForeignCurrencyAccountDto foreignCurrencyAccountDto
    ) {
        foreignCurrencyAccountService.deleteForeignCurrencyAccount(foreignCurrencyAccountDto.getId());
        return ResponseEntity.ok("Account deleted");
    }
}
