package pl.zeto.backend.VMC.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.zeto.backend.VMC.dto.CurrencyDto;
import pl.zeto.backend.VMC.model.Currency;
import pl.zeto.backend.VMC.service.CurrencyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CurrencyController {


    private final CurrencyService currencyService;


    @GetMapping("/search")
    public ResponseEntity<List<CurrencyDto>> searchCurrencies(@RequestParam String query) {
        List<CurrencyDto> results = currencyService.findByName(query);
        return ResponseEntity.ok(results);
    }


}
