package org.example.currencyaccounts.feign;


import org.example.currencyaccounts.config.FeignConfig;
import org.example.currencyaccounts.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "gateway", url = "http://localhost:8222/api", configuration = FeignConfig.class)
public interface UserClient {
    @PostMapping("v1/auth/userInfo")
    Optional<UserDto> getUserInfo();
    @PostMapping("v1/auth/findUserId/{id}")
    UserDto findUserId(@RequestParam Long id);
}
