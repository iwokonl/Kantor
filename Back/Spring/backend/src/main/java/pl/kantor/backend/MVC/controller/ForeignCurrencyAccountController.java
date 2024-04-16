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

    @PostMapping("/getCurrencyAccounts")
    public ResponseEntity<List<ForeignCurrencyAccountDto>> getCurrencyAccounts() {
        List<ForeignCurrencyAccountDto> accounts = foreignCurrencyAccountService.getAllAccountsByUserId();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/createCurrencyAccount")
    public ResponseEntity<ForeignCurrencyAccountDto> createCurrencyAccount(
            @RequestBody ForeignCurrencyAccountDto foreignCurrencyAccountDto
    ){
        ForeignCurrencyAccountDto foreignCurrencyAccountDtoToSend = foreignCurrencyAccountService.createForeignCurrencyAccount(
                foreignCurrencyAccountDto.getCurencyCode(),
                foreignCurrencyAccountDto.getBalance());
        return ResponseEntity.ok(foreignCurrencyAccountDtoToSend);
    }
}
