package org.example.paypal.config;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.example.paypal.feign.FeignErrorDecoder;
import org.example.paypal.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class FeignConfig {

    @Autowired
    @Lazy
    private PaypalService paypalService;

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = paypalService.getToken();
            if (token != null) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}