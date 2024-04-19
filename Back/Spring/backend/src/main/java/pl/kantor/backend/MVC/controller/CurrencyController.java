package pl.kantor.backend.MVC.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kantor.backend.MVC.service.CurrencyService;
import pl.kantor.backend.MVC.dto.CurrencyDto;
import pl.kantor.backend.MVC.dto.SearchCurrencyDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
@Tag(name = "Waluty")
public class CurrencyController {


    private final CurrencyService currencyService;


//TODO: Zapytać się czy robić w taki sposób @RequestBody SearchCurrencyDto query na zasadzie DTO(Zapytać się co to jest obiek domenowy) Zapytać się też kiedy używać DTO - Iwo
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
