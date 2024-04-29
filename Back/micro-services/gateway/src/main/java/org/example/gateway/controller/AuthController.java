package org.example.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.example.gateway.config.UserAuthProvider;
import org.example.gateway.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserAuthProvider userAuthProvider;
    @PostMapping("/login")
    public ResponseEntity<UserDto> login() {
//
        UserDto user = new UserDto();
        user.setId(1L);
        user.setUsername("iwo");
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }
}
