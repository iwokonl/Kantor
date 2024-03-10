package pl.zeto.backend.VMC.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zeto.backend.VMC.dto.CredentialsDto;
import pl.zeto.backend.VMC.dto.SignUpDto;
import pl.zeto.backend.VMC.dto.UserDto;
import pl.zeto.backend.VMC.exeption.AppExeption;
import pl.zeto.backend.VMC.mapper.UserMapper;
import pl.zeto.backend.VMC.model.AppAccount;
import pl.zeto.backend.VMC.model.AppUser;
import pl.zeto.backend.VMC.model.Currency;
import pl.zeto.backend.VMC.model.Role;
import pl.zeto.backend.VMC.repository.CurrencyRepo;
import pl.zeto.backend.VMC.repository.UserRepo;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepo userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AccountService accountService;
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
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));

        user.setUsername(signUpDto.username());
        AppUser savedUser = userRepository.save(user);
        AppAccount account = new AppAccount();
        account.setUser(savedUser);
        Currency currency = currencyRepository.findByName("PLN")
                .orElseThrow(() -> new AppExeption("Currency not found", HttpStatus.NOT_FOUND));
        account.setCurrency(currency);
        accountService.addAccount(account);
        return userMapper.toUserDto(savedUser);
    }
}