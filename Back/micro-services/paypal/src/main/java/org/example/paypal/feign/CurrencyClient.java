package org.example.paypal.feign;

import org.example.paypal.dto.CurrencyDto;
import org.example.paypal.dto.ForeignCurrencyAccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@FeignClient(name = "currencies-service", url = "http://localhost:8073/api")
public interface CurrencyClient {
    @GetMapping("/v1/currencies/id/{id}")
    Optional<CurrencyDto> getCurrencyById(@PathVariable("id") Long id);
}