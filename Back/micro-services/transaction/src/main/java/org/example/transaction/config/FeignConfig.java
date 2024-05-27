package org.example.transaction.config;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.example.transaction.Feign.FeignErrorDecoder;
import org.example.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class FeignConfig {

    @Autowired
    @Lazy
    private TransactionService transactionService;

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = transactionService.getToken();
            if (token != null) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}