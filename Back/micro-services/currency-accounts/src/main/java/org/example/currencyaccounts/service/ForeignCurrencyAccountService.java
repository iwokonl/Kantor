package org.example.currencyaccounts.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.currencyaccounts.dto.CurrencyDto;
import org.example.currencyaccounts.dto.ForeignCurrencyAccountDto;
import org.example.currencyaccounts.dto.UserDto;
import org.example.currencyaccounts.exeption.AppExeption;
import org.example.currencyaccounts.feign.CurrencyClient;
import org.example.currencyaccounts.feign.UserClient;
import org.example.currencyaccounts.mapper.ForeignCurrencyAccountMapper;
import org.example.currencyaccounts.model.ForeignCurrencyAccount;
import org.example.currencyaccounts.repository.ForeignCurrencyAccountRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ForeignCurrencyAccountService {

    @Autowired
    private HttpServletRequest request;
    private final CurrencyClient currencyClient;
    private final UserClient userClient;
    private final ForeignCurrencyAccountRepo foreignCurrencyAccountRepo;
    private final ForeignCurrencyAccountMapper foreignCurrencyAccountMapper;
    private final Logger logger = LoggerFactory.getLogger(ForeignCurrencyAccountService.class);

    public ForeignCurrencyAccount createForeignCurrencyAccount(String currencyId){
        Optional<UserDto> userAppDto = userClient.getUserInfo();
        if (userAppDto.isEmpty()) {
            throw new AppExeption("User not found", HttpStatus.NOT_FOUND);
        }
        UserDto userDtoReal = userAppDto.get();
        UserDto userDtoReal1 = userClient.findUserId(userDtoReal.getId());
        if (userDtoReal1.getId() == -1L) {
            throw new AppExeption("User not found", HttpStatus.NOT_FOUND);
        }


        Optional<ForeignCurrencyAccount> account = foreignCurrencyAccountRepo.findByUserIdAndCurrencyId(userDtoReal.getId(), Long.valueOf(currencyId));
        if (account.isPresent()) {
            throw new AppExeption("Account already exists", HttpStatus.BAD_REQUEST);
        }
        ForeignCurrencyAccount foreignCurrencyAccount = new ForeignCurrencyAccount();
        foreignCurrencyAccount.setBalance(BigDecimal.valueOf(0));
        Optional<CurrencyDto> currency = currencyClient.getCurrencyById(Long.valueOf(currencyId));
        if(currency.isEmpty()){
            throw new AppExeption("Currency not found", HttpStatus.NOT_FOUND);
        }
        foreignCurrencyAccount.setCurrencyId(Long.valueOf(currencyId));
        foreignCurrencyAccount.setUserId(userDtoReal.getId());
        foreignCurrencyAccountRepo.save(foreignCurrencyAccount);
        return foreignCurrencyAccount;
    }


    public String getToken(){
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7); // Odcinamy "Bearer " aby uzyskać sam token
            return jwtToken;
        }
        return null;
    }


    public List<ForeignCurrencyAccountDto> getAllAccountsByUserId() {
        Optional<UserDto> userAppDto = userClient.getUserInfo();
        if (userAppDto.isEmpty()) {
            throw new AppExeption("User not found", HttpStatus.NOT_FOUND);
        }
        UserDto userDtoReal = userAppDto.get();
        UserDto userDtoReal1 = userClient.findUserId(userDtoReal.getId());
        if (userDtoReal1.getId() == -1L) {
            throw new AppExeption("User not found", HttpStatus.NOT_FOUND);
        }
        List<ForeignCurrencyAccount> accounts = foreignCurrencyAccountRepo.findAllByUserId(userDtoReal.getId());
        return foreignCurrencyAccountMapper.toForeignCurrencyAccountDtoList(accounts);
    }

    public void deleteForeignCurrencyAccount(Long id) {
        Optional<UserDto> userAppDto = userClient.getUserInfo();
        if (userAppDto.isEmpty()) {
            throw new AppExeption("User not found", HttpStatus.NOT_FOUND);
        }
        UserDto userDtoReal = userAppDto.get();
        UserDto userDtoReal1 = userClient.findUserId(userDtoReal.getId());
        if (userDtoReal1.getId() == -1L) {
            throw new AppExeption("User not found", HttpStatus.NOT_FOUND);
        }
        Optional<ForeignCurrencyAccount> account = foreignCurrencyAccountRepo.findByUserIdAndId(userDtoReal.getId(), id);
        if (account.isEmpty()) {
            throw new AppExeption("Account is not yours", HttpStatus.NOT_FOUND);
        }
        try {
            foreignCurrencyAccountRepo.deleteById(id);
        } catch (Exception e) {
            throw new AppExeption("Account not found", HttpStatus.NOT_FOUND);
        }

    }

    public ForeignCurrencyAccountDto findByCurrencyCodeAndUserId(String currencyId, Long userId) {
        Optional<ForeignCurrencyAccount> account = foreignCurrencyAccountRepo.findByCurrencyIdAndUserId(Long.valueOf(currencyId), userId);
        if (account.isEmpty()) {
            throw new AppExeption("Account not found", HttpStatus.NOT_FOUND);
        }
        return foreignCurrencyAccountMapper.toForeignCurrencyAccountDto(account.get());
    }

    public void save(ForeignCurrencyAccountDto foreignCurrencyAccountDto) {


        ForeignCurrencyAccount foreignCurrencyAccount = new ForeignCurrencyAccount();

        foreignCurrencyAccount.setBalance(new BigDecimal(foreignCurrencyAccountDto.getBalance()));

        foreignCurrencyAccount.setCurrencyId(Long.valueOf(foreignCurrencyAccountDto.getCurrencyId()));

        foreignCurrencyAccount.setUserId(foreignCurrencyAccountDto.getUserId());
        foreignCurrencyAccount.setId(foreignCurrencyAccountDto.getId());
        foreignCurrencyAccountRepo.save(foreignCurrencyAccount);
    }
}
