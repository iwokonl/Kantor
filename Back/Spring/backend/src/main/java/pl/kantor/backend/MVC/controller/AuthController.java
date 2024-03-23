package pl.kantor.backend.MVC.controller;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.kantor.backend.MVC.config.UserAuthProvider;
import pl.kantor.backend.MVC.dto.CredentialsDto;
import pl.kantor.backend.MVC.dto.SignUpDto;
import pl.kantor.backend.MVC.dto.UserDto;
import pl.kantor.backend.MVC.service.UserService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authorization")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserAuthProvider userAuthProvider;
    private final UserService userService;

    @PostMapping("/userinfo")
    public ResponseEntity<String> currentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return ResponseEntity.ok(currentUserName);
    }

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

}