package org.example.gateway.service;

import lombok.RequiredArgsConstructor;
import org.example.gateway.dto.CredentialsDto;
import org.example.gateway.dto.SignUpDto;
import org.example.gateway.dto.UserDto;
import org.example.gateway.exeption.AppExeption;
import org.example.gateway.mapper.UserMapper;
import org.example.gateway.model.AppUser;
import org.example.gateway.model.Role;
import org.example.gateway.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepo userRepository;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserDto login(CredentialsDto credentialsDto) {

        AppUser user = userRepository.findByUsername(credentialsDto.username())
                .orElseThrow(() -> new AppExeption("User not found", "gateway", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
            return userMapper.toUserDto(user);

        }
        throw new AppExeption("Invalid password", "gateway", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto signUpDto) {
        Optional<AppUser> oUser = userRepository.findByUsername(signUpDto.username());

        if (oUser.isPresent()) {
            throw new AppExeption("User already exists", "gateway", HttpStatus.BAD_REQUEST);
        }
        AppUser user = userMapper.signUpToUser(signUpDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));
        user.setRole(Role.USER);


        user.setUsername(signUpDto.username());
        AppUser savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    public UserDto getUserInfo(String jwtToken) {
        return jwtInfo(jwtToken);
    }

    public UserDto jwtInfo(String token) {
        Pattern pattern = Pattern.compile("UserDto\\(id=(.*?), role=(.*?), username=(.*?), firstName=(.*?), lastName=(.*?), email=(.*?), token=(.*?), expTime=(.*?)\\)");
        Matcher matcher = pattern.matcher(token);

        if (matcher.find()) {
            UserDto userInfo = new UserDto();
            userInfo.setId(Long.valueOf(matcher.group(1)));
            userInfo.setRole(matcher.group(2).equals("ADMIN") ? Role.ADMIN : Role.USER);
            userInfo.setUsername(matcher.group(3));
            userInfo.setFirstName(matcher.group(4));
            userInfo.setLastName(matcher.group(5));
            userInfo.setEmail(matcher.group(6));
            userInfo.setToken(matcher.group(7));
            userInfo.setExpTime(matcher.group(8));
            return userInfo;
        }

        throw new AppExeption("Invalid token", "gateway", HttpStatus.BAD_REQUEST);
    }

    public UserDto findUserId(Long id) {
        Optional<AppUser> user = userRepository.findById(id);
        AppUser user1 = null;
        if (user.isEmpty()) {
            user1 = new AppUser();
            user1.setId(-1L);
        } else {
            user1 = user.get();
        }
        return userMapper.toUserDto(user1);
    }

    public void loginChange(UserDto userDto) {
        Optional<AppUser> user = userRepository.findById(userDto.getId());
        if (user.isEmpty()) {
            throw new AppExeption("User not found", "gateway", HttpStatus.NOT_FOUND);
        }
        AppUser user1 = user.get();
        user1.setId(user.get().getId());
        user1.setUsername(userDto.getUsername());
        userRepository.save(user1);
    }

    public void firstnameChange(UserDto userDto) {
        Optional<AppUser> user = userRepository.findById(userDto.getId());
        if (user.isEmpty()) {
            throw new AppExeption("User not found", "gateway", HttpStatus.NOT_FOUND);
        }
        AppUser user1 = user.get();
        user1.setId(user.get().getId());
        user1.setFirstName(userDto.getFirstName());
        userRepository.save(user1);
    }

    public void lastnameChange(UserDto userDto) {
        Optional<AppUser> user = userRepository.findById(userDto.getId());
        if (user.isEmpty()) {
            throw new AppExeption("User not found", "gateway", HttpStatus.NOT_FOUND);
        }
        AppUser user1 = user.get();
        user1.setId(user.get().getId());
        user1.setLastName(userDto.getLastName());
        userRepository.save(user1);
    }

    public void emailChange(UserDto userDto) {
        Optional<AppUser> user = userRepository.findById(userDto.getId());
        if (user.isEmpty()) {
            throw new AppExeption("User not found", "gateway", HttpStatus.NOT_FOUND);
        }
        AppUser user1 = user.get();
        user1.setId(user.get().getId());
        user1.setEmail(userDto.getEmail());
        userRepository.save(user1);
    }

    public void passwordChange(UserDto userDto, String password, String newPassword) {
        AppUser user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new AppExeption("User not found", "gateway", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(CharBuffer.wrap(password), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(CharBuffer.wrap(newPassword)));
            userRepository.save(user);
        }
    }
}
