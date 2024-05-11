package org.example.currencyaccounts.controller;

import lombok.RequiredArgsConstructor;
import org.example.currencyaccounts.dto.CurrencyIdDto;
import org.example.currencyaccounts.dto.ForeignCurrencyAccountDto;
import org.example.currencyaccounts.dto.ForeignCurrencyAccountIdDto;
import org.example.currencyaccounts.model.ForeignCurrencyAccount;
import org.example.currencyaccounts.service.ForeignCurrencyAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/currencyAccounts")
public class ForeignCurrencyAccountController {
    private final ForeignCurrencyAccountService foreignCurrencyAccountService;


    @PostMapping("/createCurrencyAccount")
    public ResponseEntity<ForeignCurrencyAccount> createCurrencyAccount(
            @RequestBody CurrencyIdDto currencyIdDto) {
        ForeignCurrencyAccount foreignCurrencyAccountDtoToSend =
                foreignCurrencyAccountService.createForeignCurrencyAccount(currencyIdDto.id());
        return ResponseEntity.ok(foreignCurrencyAccountDtoToSend);
    }

    @PostMapping("/getCurrencyAccounts")
    public ResponseEntity<List<ForeignCurrencyAccountDto>> getCurrencyAccounts() {
        List<ForeignCurrencyAccountDto> accounts = foreignCurrencyAccountService.getAllAccountsByUserId();
        return ResponseEntity.ok(accounts);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/deleteCurrencyAccount")
    public void deleteCurrencyAccount(
            @RequestBody ForeignCurrencyAccountIdDto foreignCurrencyAccountIdDto
    ) {
        foreignCurrencyAccountService.deleteForeignCurrencyAccount(Long.valueOf(foreignCurrencyAccountIdDto.id()));

    }

    @GetMapping("/id/{id}/currencyCode/{currencyCode}")
    ResponseEntity<ForeignCurrencyAccountDto> findByCurrencyCodeAndUserId(
            @PathVariable("currencyCode") String currencyCode,
            @PathVariable("id") Long userId
    ) {
        ForeignCurrencyAccountDto account = foreignCurrencyAccountService.findByCurrencyCodeAndUserId(currencyCode, userId);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/save")
    void save(@RequestBody ForeignCurrencyAccountDto foreignCurrencyAccountDto) {
        foreignCurrencyAccountService.save(foreignCurrencyAccountDto);
    }

}
