package org.example.currencies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication

public class CurrenciesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrenciesApplication.class, args);
    }

}
