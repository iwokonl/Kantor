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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepo userRepository;
    private final UserMapper userMapper;
    public UserDto login(CredentialsDto credentialsDto){

        AppUser user = userRepository.findByUsername(credentialsDto.username())
                .orElseThrow(() -> new AppExeption("User not found","gateway", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
            return userMapper.toUserDto(user);

        }
        throw new AppExeption("Invalid password","gateway", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto signUpDto) {
        Optional<AppUser> oUser = userRepository.findByUsername(signUpDto.username());

        if (oUser.isPresent()) {
            throw new AppExeption("User already exists","gateway", HttpStatus.BAD_REQUEST);
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
        Pattern pattern = Pattern.compile("UserDto\\(id=(.*?), role=(.*?), username=(.*?), firstName=(.*?), lastName=(.*?), email=(.*?), token=(.*?)\\)");
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
            return userInfo;
        }

        throw new AppExeption("Invalid token","gateway", HttpStatus.BAD_REQUEST);
    }
    public UserDto findUserId(Long id) {
        Optional<AppUser> user = userRepository.findById(id);
        AppUser user1 = null;
        if (user.isEmpty()) {
            user1 = new AppUser();
            user1.setId(-1L);
        }
        else {
            user1 = user.get();
        }
        return userMapper.toUserDto(user1);
    }
}
