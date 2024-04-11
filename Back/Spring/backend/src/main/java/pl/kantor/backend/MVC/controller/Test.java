package pl.kantor.backend.MVC.controller;

import com.paypal.base.rest.APIContext;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.auth0.jwt.JWT;
import org.springframework.web.reactive.function.client.WebClient;
import pl.kantor.backend.MVC.config.UserAuthProvider;
import pl.kantor.backend.MVC.dto.ForeignCurrencyAccountDto;
import pl.kantor.backend.MVC.mapper.CurrencyMapper;
import pl.kantor.backend.MVC.mapper.ForeignCurrencyAccountMapper;
import pl.kantor.backend.MVC.model.ForeignCurrencyAccount;
import pl.kantor.backend.MVC.repository.ForeignCurrencyAccountRepo;
import pl.kantor.backend.MVC.service.UserService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class Test {
    private final APIContext apiContext;
    private final ForeignCurrencyAccountRepo foreignCurrencyAccountRepo;
    private final UserAuthProvider userAuthProvider;
    private final UserService userService;
    private final WebClient webClient;

    @PostMapping("/test")
    public ResponseEntity<String> test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Map<String, String> response1 = userService.jwtInfo(currentUserName);
        String token = response1.get("token");

        String response2 = webClient.get().uri("http://localhost:8082/api/authorization/userinfo").header("Authorization", "Bearer " + token).retrieve().bodyToMono(String.class).block();

        return ResponseEntity.ok(token);
    }
}
