package org.example.paypal.feign;

import org.example.paypal.dto.CurrencyDto;
import org.example.paypal.dto.ForeignCurrencyAccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@FeignClient(name = "currencies-account", url = "http://localhost:8072/api")
public interface CurrencyAccountFeigin {

    @GetMapping("/v1/currencyAccounts/id/{id}/currencyCode/{currencyCode}")
    Optional<ForeignCurrencyAccountDto> findByCurrencyCodeAndUserId(@PathVariable("currencyCode") String currencyCode, @PathVariable("id") Long userId);

    @PostMapping("/v1/currencyAccounts/save")
    void save(ForeignCurrencyAccountDto foreignCurrencyAccountDto);
}
