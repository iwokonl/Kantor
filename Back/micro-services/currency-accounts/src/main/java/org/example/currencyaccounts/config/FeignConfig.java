package org.example.currencyaccounts.config;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.currencyaccounts.feign.FeignErrorDecoder;
import org.example.currencyaccounts.service.ForeignCurrencyAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@RequiredArgsConstructor
@Configuration
public class FeignConfig {

    @Autowired
    private final ForeignCurrencyAccountService foreignCurrencyAccountService;
    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = foreignCurrencyAccountService.getToken();
            if (token != null) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }


}