package pl.kantor.backend.MVC.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authorization")
@Tag(name = "Autoryzacja i informacje o użytkowniku")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserAuthProvider userAuthProvider;
    private final UserService userService;


    @Operation(
            description = "Pobranie informacji o użytkowniku",
            summary = "Pobranie informacji o użytkowniku",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pomyślnie pobrano informacje o użytkowniku"),
                    @ApiResponse(responseCode = "401", description = "Nieautoryzowany dostęp"),
                    @ApiResponse(responseCode = "403", description = "Brak dostępu")
            }
    )
    @PostMapping("/userinfo")
    public ResponseEntity<Map<String,String>> currentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Map<String,String> response = userService.jwtInfo(currentUserName);
        return ResponseEntity.ok(response);
    }


    @Operation(
            description = "Logowanie użytkownika",
            summary = "Logowanie użytkownika",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pomyślnie zalogowano użytkownika"),
                    @ApiResponse(responseCode = "400", description = "Invalid password"),
                    @ApiResponse(responseCode = "500", description = "Błąd serwera")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = CredentialsDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "default",
                                            value = "{\"username\": \"iwo\", \"password\": \"iwo\"}"
                                    )
                            }
                    )
            )
    )
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto coridentialsDto) {
//
        UserDto user = userService.login(coridentialsDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }


    @Operation(
            description = "Rejestracja użytkownika",
            summary = "Rejestracja użytkownika",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pomyślnie zarejestrowano użytkownika"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane rejestracji"),
                    @ApiResponse(responseCode = "500", description = "Błąd serwera")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = SignUpDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "default",
                                            value = "{\"firstName\": \"Iwo\", \"lastName\": \"Stanisławski\", \"email\": \"iwo@iwo.pl\", \"username\": \"iwo\", \"password\": \"iwo\"}"
                                    )
                            }
                    )
            )
    )
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        UserDto user = userService.register(signUpDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }

}