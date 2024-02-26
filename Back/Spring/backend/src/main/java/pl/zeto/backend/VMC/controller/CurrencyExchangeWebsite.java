package pl.zeto.backend.VMC.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Currency") // Określa bazową ścieżkę dla wszystkich metod w tym kontrolerze
public class CurrencyExchangeWebsite {

    @GetMapping("/USD")
    public String home() {
        return "Strona dla USD";
    }
}