package pl.zeto.backend.VMC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.zeto.backend.VMC.model.CurrencyResponse;
import pl.zeto.backend.VMC.service.CurrencyService;

@RestController
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/api/currency/{currencyCode}")
    public CurrencyResponse getCurrencyInfo(@PathVariable String currencyCode) {
        return currencyService.getCurrencyRate(currencyCode);
    }
}