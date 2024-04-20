package pl.kantor.backend.MVC.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PayoutBatch;
import com.paypal.base.rest.PayPalRESTException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import pl.kantor.backend.MVC.config.UserAuthProvider;
import pl.kantor.backend.MVC.dto.PaymentPaypalDto;
import pl.kantor.backend.MVC.dto.PayoutRequestPaypalDto;
import pl.kantor.backend.MVC.exeption.AppExeption;
import pl.kantor.backend.MVC.service.PaypalService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kantor.backend.MVC.service.UserService;

import java.util.Map;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
@Tag(name = "Paypal")
public class PaypalController {


    private final PaypalService paypalService;
    private final UserAuthProvider userAuthProvider;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(PaypalController.class);

    @Operation(
            description = "Stworzenie płatności",
            summary = "Stworzenie płatności",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pomyślnie zarejestrowano użytkownika"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane rejestracji"),
                    @ApiResponse(responseCode = "500", description = "Błąd serwera")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = PaymentPaypalDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "default",
                                            value = "{\"total\": 100.02, \"currency\": \"PLN\", \"method\": \"paypal\", \"intent\": \"sale\", \"description\": \"desc\", \"cancelUrl\": \"http://localhost:8082/api/payment/cancel\", \"successUrl\": \"http://localhost:8082/api/payment/success\"}"
                                    )
                            }
                    )
            )
    )

    @PostMapping("/create")
    public ResponseEntity<RedirectView> createPayment(@RequestBody PaymentPaypalDto paymentPaypalDto) {
        if(paymentPaypalDto.total() <= 0){
            throw new AppExeption("Amount must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        try {
//            String cancelUrl = "http://localhost:8082/api/payment/cancel";
//            String successUrl = "http://localhost:8082/api/payment/success";

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            Map<String, String> response = userService.jwtInfo(currentUserName);
            String userId = response.get("id");
            String token = response.get("token");

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            String successUrl = paymentPaypalDto.successUrl() + "?userId=" + userId + "&JWTtoken=" + token;
            Payment payment = paypalService.createPayment(
                    paymentPaypalDto.total(),
                    paymentPaypalDto.currency(),
                    paymentPaypalDto.method(),
                    paymentPaypalDto.intent(),
                    paymentPaypalDto.description(),
                    paymentPaypalDto.cancelUrl(),
                    successUrl
            );

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return ResponseEntity.ok().headers(headers).body(new RedirectView(links.getHref()));
                }
            }
        } catch (PayPalRESTException e) {
            throw new AppExeption("Error occurred while processing payment", HttpStatus.EXPECTATION_FAILED);

        }
        return ResponseEntity.ok(new RedirectView("/error"));
    }

    @Operation(
            description = "Stworzenie wypłaty",
            summary = "Stworzenie wypłaty",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully created payout"),
                    @ApiResponse(responseCode = "400", description = "Invalid payout data"),
                    @ApiResponse(responseCode = "500", description = "Server error")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = PayoutRequestPaypalDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "default",
                                            value = "{\"receiverEmail\": \"kupujacy@kantrol.pl\", \"total\": 100.00, \"currency\": \"PLN\"}"
                                    )
                            }
                    )
            )
    )

    @PostMapping("/createPayout")
    public ResponseEntity<PayoutBatch> createPayout(@RequestBody PayoutRequestPaypalDto payoutRequestPaypalDto) throws PayPalRESTException, AppExeption {
        if (payoutRequestPaypalDto.total() <= 0) {
            throw new AppExeption("Amount must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            Map<String, String> response = userService.jwtInfo(currentUserName);
            String userId = response.get("id");
            paypalService.removeAmountToKantorAccount(payoutRequestPaypalDto.currency(), userId, payoutRequestPaypalDto.total());


            PayoutBatch payoutBatch = paypalService.createPayout(
                    payoutRequestPaypalDto.receiverEmail(),
                    payoutRequestPaypalDto.total(),
                    payoutRequestPaypalDto.currency()
            );

            return ResponseEntity.ok(payoutBatch);
        } catch (AppExeption e) {
            throw new AppExeption(e.getMessage(), e.getHttpStatus());
        }
    }

    @Operation(
            description = "Przekierowanie po pozytywnym zakończeniu płatności.",
            summary = "Przekierowanie po pozytywnym zakończeniu płatności. Jeśli płatność wykona się poprawnie to dodaje kwotę do konta kantoru oraz wykonuje płatność. " +
                    "Część dalszej logiki /create",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pomyślne wykonana operacja"),
                    @ApiResponse(responseCode = "500", description = "Błąd serwera")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = PaymentPaypalDto.class),
                            examples = {
                                    @ExampleObject()
                            }
                    )
            )
    )
    @GetMapping("/success")
    public RedirectView successPayment(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @RequestParam("userId") String userId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                paypalService.addAmountToKantorAccount(payment, userId);
                return new RedirectView("http://localhost:4200");
            }
        } catch (PayPalRESTException e) {
            throw new AppExeption("Error occurred while processing payment", HttpStatus.EXPECTATION_FAILED);
        }
        return new RedirectView("http://localhost:8082/api/payment/error");
    }


    @Operation(
            description = "Przekierowanie po anulowaniu płatności.",
            summary = "Przekierowanie po anulowaniu płatności. Jeśli płatność zostanie anulowana to przekierowuje na odpowiednią stronę. " +
                    "Część dalszej logiki /create",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pomyślne przekierowanie"),
                    @ApiResponse(responseCode = "500", description = "Błąd serwera")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = PaymentPaypalDto.class),
                            examples = {
                                    @ExampleObject()
                            }
                    )
            )
    )
    @PostMapping("/cancel")
    public RedirectView cancelPayment() {
        return new RedirectView("http://localhost:4200/payment/cancel");
    }


    @Operation(
            description = "Przekierowanie po błędzie podczas płatności.",
            summary = "Przekierowanie po błędzie podczas płatności. Jeśli podczas płatności wsytąpi błąd to przekierowuje na odpowiednią stronę. " +
                    "Część dalszej logiki /create",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pomyślne przekierowanie"),
                    @ApiResponse(responseCode = "500", description = "Błąd serwera")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = PaymentPaypalDto.class),
                            examples = {
                                    @ExampleObject()
                            }
                    )
            )
    )
    @PostMapping("/error")
    public RedirectView errorPayment() {
        return new RedirectView("http://localhost:4200/payment/error");
    }
}