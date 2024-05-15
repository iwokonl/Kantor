package org.example.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.example.gateway.config.UserAuthProvider;
import org.example.gateway.dto.CredentialsDto;
import org.example.gateway.dto.SignUpDto;
import org.example.gateway.dto.UserDto;
import org.example.gateway.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserAuthProvider userAuthProvider;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto coridentialsDto) {
//
        UserDto user = userService.login(coridentialsDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        UserDto user = userService.register(signUpDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }

    @PostMapping("/findUserId/{id}")
    public ResponseEntity<UserDto> findUserId(@RequestParam Long id) {
        logger.error("asdd: " + id);
        UserDto user = userService.findUserId(id);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/userInfo")
    public ResponseEntity<UserDto> userInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getName();
        UserDto user = userService.getUserInfo(token);
        return ResponseEntity.ok(user);
    }
}
