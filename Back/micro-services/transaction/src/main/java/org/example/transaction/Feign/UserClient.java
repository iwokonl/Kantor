package org.example.transaction.Feign;

import org.example.transaction.config.FeignConfig;
import org.example.transaction.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@FeignClient(name = "gateway", url = "http://localhost:8222/api", configuration = FeignConfig.class)
public interface UserClient {
    @PostMapping("v1/auth/userInfo")
    Optional<UserDto> getUserInfo();
}

