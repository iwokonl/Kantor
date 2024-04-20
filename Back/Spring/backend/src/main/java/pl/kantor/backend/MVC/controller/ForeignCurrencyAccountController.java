package pl.kantor.backend.MVC.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.kantor.backend.MVC.dto.ForeignCurrencyAccountDto;
import pl.kantor.backend.MVC.dto.PaymentPaypalDto;
import pl.kantor.backend.MVC.dto.UserIdDto;
import pl.kantor.backend.MVC.model.ForeignCurrencyAccount;
import pl.kantor.backend.MVC.service.ForeignCurrencyAccountService;
import pl.kantor.backend.MVC.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ForeignCurrencyAccount")
@Tag(name = "Konta walutowe")
public class ForeignCurrencyAccountController {
    private final ForeignCurrencyAccountService foreignCurrencyAccountService;


    @Operation(
            description = "Pobranie konta walutowego",
            summary = "Pobranie konta walutowego",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pomyślnie pobrano konto walutowe"),
                    @ApiResponse(responseCode = "500", description = "Błąd serwera")
            }
    )
    @PostMapping("/getCurrencyAccounts")
    public ResponseEntity<List<ForeignCurrencyAccountDto>> getCurrencyAccounts() {
        List<ForeignCurrencyAccountDto> accounts = foreignCurrencyAccountService.getAllAccountsByUserId();
        return ResponseEntity.ok(accounts);
    }


//TODO: Dodać customowe dto jak będzie mi się nudziło bo z ForeignCurrencyAccountDto są zajęte tylko dwa pola - Iwo
@Operation(
        description = "Stworzenie konta walutowego",
        summary = "Stworzenie konta walutowego. W tym przypadku CAD(dolar kanadyjski).",
        responses = {
                @ApiResponse(responseCode = "200", description = "Pomyślnie stworzono konto walutowe"),
                @ApiResponse(responseCode = "400", description = "Niepoprawne dane wejściowe"),
                @ApiResponse(responseCode = "500", description = "Błąd serwera")
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(
                        schema = @Schema(implementation = ForeignCurrencyAccountDto.class),
                        examples = {
                                @ExampleObject(
                                        name = "default",
                                        value = "{\n" +
                                                "    \"curencyCode\": \"CAD\",\n" +
                                                "    \"balance\": 0\n" +
                                                "}"
                                )
                        }
                )
        )
)
    @PostMapping("/createCurrencyAccount")
    public ResponseEntity<ForeignCurrencyAccountDto> createCurrencyAccount(
            @RequestBody ForeignCurrencyAccountDto foreignCurrencyAccountDto
    ){
        ForeignCurrencyAccountDto foreignCurrencyAccountDtoToSend = foreignCurrencyAccountService.createForeignCurrencyAccount(
                foreignCurrencyAccountDto.getCurencyCode(),
                foreignCurrencyAccountDto.getBalance());
        return ResponseEntity.ok(foreignCurrencyAccountDtoToSend);
    }

    @Operation(
            description = "Usuwanie konta walutowego",
            summary = "Usuwanie konta walutowego. W tym przypadku CAD(dolar kanadyjski).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pomyślnie usunięto konto walutowe"),
                    @ApiResponse(responseCode = "400", description = "Niepoprawne dane wejściowe"),
                    @ApiResponse(responseCode = "500", description = "Błąd serwera")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = ForeignCurrencyAccountDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "default",
                                            value = "{\n" +
                                                    "    \"id\":1\n" +
                                                    "}"
                                    )
                            }
                    )
            )
    )
    @DeleteMapping("/deleteCurrencyAccount")
    public ResponseEntity<String> deleteCurrencyAccount(
            @RequestBody ForeignCurrencyAccountDto foreignCurrencyAccountDto
    ){
        foreignCurrencyAccountService.deleteForeignCurrencyAccount(foreignCurrencyAccountDto.getId());
        return ResponseEntity.ok("Account deleted");
    }
}
