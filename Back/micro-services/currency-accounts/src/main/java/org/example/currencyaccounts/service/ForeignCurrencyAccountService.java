package org.example.currencyaccounts.service;

import lombok.RequiredArgsConstructor;
import org.example.currencyaccounts.dto.ForeignCurrencyAccountDto;
import org.example.currencyaccounts.exeption.AppExeption;
import org.example.currencyaccounts.mapper.ForeignCurrencyAccountMapper;
import org.example.currencyaccounts.model.ForeignCurrencyAccount;
import org.example.currencyaccounts.repository.ForeignCurrencyAccountRepo;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ForeignCurrencyAccountService {

    private final CurrencyRepo currencyRepo;
    private final UserRepo userRepo;
    private final ForeignCurrencyAccountRepo foreignCurrencyAccountRepo;
    private final ForeignCurrencyAccountMapper foreignCurrencyAccountMapper;
    @Lazy
    private final UserService userService;


    public ForeignCurrencyAccount addAccount(ForeignCurrencyAccount account) {
        account.setBalance(new BigDecimal(0));

        return foreignCurrencyAccountRepo.save(account);
    }

    public ForeignCurrencyAccountDto createForeignCurrencyAccount(String code, String balance) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Map<String,String> response = userService.jwtInfo(currentUserName);
        Optional<AppUser> user = userRepo.findById(Long.valueOf(response.get("id")));

        Optional<ForeignCurrencyAccount> account = foreignCurrencyAccountRepo.findByUserIdAndCurrencyCode(Long.valueOf(response.get("id")), code);
        if (account.isPresent()) {
            throw new AppExeption("Account already exists", HttpStatus.BAD_REQUEST);
        }
        ForeignCurrencyAccount foreignCurrencyAccount = new ForeignCurrencyAccount();
        foreignCurrencyAccount.setBalance(BigDecimal.valueOf(Long.parseLong(balance)));

        Optional<Currency> currency = currencyRepo.findByCode(code.toUpperCase());
        Currency currencyReal;
        if (currency.isPresent()) {
            currencyReal = currency.get();
        }
        else {
            throw new AppExeption("Currency not found", HttpStatus.NOT_FOUND);
        }

        foreignCurrencyAccount.setCurrency(currencyReal);

        AppUser userReal = null;
        if (user.isPresent()) {
            userReal = user.get();
        }
        foreignCurrencyAccount.setUser(userReal);
        ForeignCurrencyAccountDto foreignCurrencyAccountDto = foreignCurrencyAccountMapper.ForeignCurrencyAccounttoForeignCurrencyAccountDto(foreignCurrencyAccount);
        foreignCurrencyAccountRepo.save(foreignCurrencyAccount);

        return foreignCurrencyAccountDto;
    }
    public ForeignCurrencyAccount getAccount(Long id) {
        return foreignCurrencyAccountRepo.findById(id).orElse(null);
    }

    public List<ForeignCurrencyAccountDto> getAllAccountsByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Map<String,String> response = userService.jwtInfo(currentUserName);
        String userId = response.get("id");
        List<ForeignCurrencyAccount> accounts = foreignCurrencyAccountRepo.findAllByUserId(Long.valueOf(userId));
        return accounts.stream()
                .map(account -> new ForeignCurrencyAccountDto(
                        account.getId(),
                        account.getCurrency().getCode(),
                        account.getCurrency().getName(),
                        account.getBalance().toString(),
                        account.getUser().getId()
                ))
                .collect(Collectors.toList());
    }

    public ForeignCurrencyAccount addAmount(Long accountId, BigDecimal amount) {
        ForeignCurrencyAccount account = foreignCurrencyAccountRepo.findById(accountId)
                .orElseThrow(() -> new AppExeption("Account not found", HttpStatus.NOT_FOUND));
        account.setBalance(account.getBalance().add(amount));
        return foreignCurrencyAccountRepo.save(account);
    }

    public void deleteForeignCurrencyAccount(Long id) {
        foreignCurrencyAccountRepo.
                deleteById(id);
    }
}
