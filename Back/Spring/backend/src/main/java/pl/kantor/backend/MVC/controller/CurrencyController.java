package pl.kantor.backend.MVC.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Operation(
        description = "Wyszukiwanie walut",
        summary = "Wyszukiwanie walut",
        responses = {
                @ApiResponse(responseCode = "200", description = "Pomyślnie znaleziono waluty"),
                @ApiResponse(responseCode = "500", description = "Błąd serwera")
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(
                        schema = @Schema(implementation = SearchCurrencyDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "default",
                                        value = "{\"name\": \"as\"}"
                                )
                        }
                )
        )
)
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
