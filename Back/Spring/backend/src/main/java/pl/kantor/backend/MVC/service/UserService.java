package pl.kantor.backend.MVC.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kantor.backend.MVC.dto.CredentialsDto;
import pl.kantor.backend.MVC.dto.SignUpDto;
import pl.kantor.backend.MVC.dto.UserDto;
import pl.kantor.backend.MVC.exeption.AppExeption;
import pl.kantor.backend.MVC.mapper.UserMapper;
import pl.kantor.backend.MVC.model.ForeignCurrencyAccount;
import pl.kantor.backend.MVC.model.AppUser;
import pl.kantor.backend.MVC.model.Currency;
import pl.kantor.backend.MVC.model.Role;
import pl.kantor.backend.MVC.repository.CurrencyRepo;
import pl.kantor.backend.MVC.repository.UserRepo;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepo userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final ForeignCurrencyAccountService foreignCurrencyAccountService;
    private final CurrencyRepo currencyRepository;

    public UserDto login(CredentialsDto credentialsDto){

        AppUser user = userRepository.findByUsername(credentialsDto.username())
                .orElseThrow(() -> new AppExeption("User not found", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
            return userMapper.toUserDto(user);

        }
        throw new AppExeption("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto signUpDto) {
        Optional<AppUser> oUser = userRepository.findByUsername(signUpDto.username());

        if (oUser.isPresent()) {
            throw new AppExeption("User already exists", HttpStatus.BAD_REQUEST);
        }
        AppUser user = userMapper.signUpToUser(signUpDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));
        user.setRole(Role.USER);


        user.setUsername(signUpDto.username());
        AppUser savedUser = userRepository.save(user);
        ForeignCurrencyAccount account = new ForeignCurrencyAccount();
        account.setUser(savedUser);
        Currency currency = currencyRepository.findByCode("PLN")
                .orElseThrow(() -> new AppExeption("Currency not found", HttpStatus.NOT_FOUND));
        account.setCurrency(currency);
        foreignCurrencyAccountService.addAccount(account);
        return userMapper.toUserDto(savedUser);
    }
}