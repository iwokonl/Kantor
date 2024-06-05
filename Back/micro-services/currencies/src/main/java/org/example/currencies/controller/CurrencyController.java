package org.example.currencies.controller;

import lombok.RequiredArgsConstructor;
import org.example.currencies.dto.CurrencyDto;
import org.example.currencies.dto.SearchCurrencyDto;
import org.example.currencies.repository.CurrencyRepo;
import org.example.currencies.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/currencies")
public class CurrencyController {
    private final CurrencyService currencyService;

    @PostMapping("/search")
    public ResponseEntity<List<CurrencyDto>> searchCurrencies(@RequestBody SearchCurrencyDto query) {
        List<CurrencyDto> results = currencyService.findByName(query);
        return ResponseEntity.ok(results);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<CurrencyDto> getCurrencyById(@PathVariable("id") Long id) {
        CurrencyDto currency = currencyService.findById(id);
        return ResponseEntity.ok(currency);
    }
}
