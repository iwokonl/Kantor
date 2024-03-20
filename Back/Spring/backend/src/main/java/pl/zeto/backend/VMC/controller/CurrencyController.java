package pl.zeto.backend.VMC.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zeto.backend.VMC.dto.CurrencyDto;
import pl.zeto.backend.VMC.dto.SearchCurrencyDto;
import pl.zeto.backend.VMC.model.Currency;
import pl.zeto.backend.VMC.service.CurrencyService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {


    private final CurrencyService currencyService;


    @PostMapping("/search")
    public ResponseEntity<List<CurrencyDto>> searchCurrencies(@RequestBody SearchCurrencyDto query) {
        List<CurrencyDto> results = currencyService.findByName(query);
        return ResponseEntity.ok(results);
    }
//    @PostMapping("/messages")
//    public ResponseEntity<List<String>> searchCurrencies(@RequestBody SearchCurrencyDto query) {
//        List<CurrencyDto> results = currencyService.findByName(query);
//        List<String> rr = new ArrayList<>();
//        for (CurrencyDto currencyDto : results) {
//            rr.add(currencyDto.getName());
//        }
//        return ResponseEntity.ok(rr);
//    }

}
