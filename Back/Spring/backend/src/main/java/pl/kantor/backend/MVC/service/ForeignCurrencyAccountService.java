package pl.kantor.backend.MVC.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.kantor.backend.MVC.dto.ForeignCurrencyAccountDto;
import pl.kantor.backend.MVC.exeption.AppExeption;
import pl.kantor.backend.MVC.mapper.ForeignCurrencyAccountMapper;
import pl.kantor.backend.MVC.repository.AccountRepo;
import pl.kantor.backend.MVC.model.ForeignCurrencyAccount;
import pl.kantor.backend.MVC.repository.ForeignCurrencyAccountRepo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ForeignCurrencyAccountService {


    private final AccountRepo accountRepository;


    private final ForeignCurrencyAccountRepo foreignCurrencyAccountRepo;

    public ForeignCurrencyAccount addAccount(ForeignCurrencyAccount account) {
        account.setBalance(new BigDecimal(0));

        return accountRepository.save(account);
    }

    public ForeignCurrencyAccount getAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public List<ForeignCurrencyAccountDto> getAllAccountsByUserId(Long userId) {
        List<ForeignCurrencyAccount> accounts = foreignCurrencyAccountRepo.findAllByUserId(userId);
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
}