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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepo userRepository;
    private final UserMapper userMapper;
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
        return userMapper.toUserDto(savedUser);
    }
}
