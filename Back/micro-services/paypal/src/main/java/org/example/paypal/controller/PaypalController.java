package org.example.paypal.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PayoutBatch;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.example.paypal.dto.*;
import org.example.paypal.exeption.AppExeption;
import org.example.paypal.feign.CurrencyClient;
import org.example.paypal.feign.TranactionClient;
import org.example.paypal.feign.UserClient;
import org.example.paypal.service.ApiService;
import org.example.paypal.service.PaypalService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/paypal")
public class PaypalController {

    private final PaypalService paypalService;
    private final UserClient userService;
    private final CurrencyClient currencyClient;
    private static final Logger logger = LoggerFactory.getLogger(PaypalController.class);
    private final TranactionClient tranactionClient;
    private WebClient webClient;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/create")
    public ResponseEntity<RedirectView> createPayment(@RequestBody PaymentPaypalDto paymentPaypalDto) {
        if (paymentPaypalDto.total() <= 0) {
            throw new AppExeption("Amount must be greater than 0", "Paypal", HttpStatus.BAD_REQUEST);
        }
        try {
            logger.info("Create payment");

//            String cancelUrl = "http://localhost:8082/api/payment/cancel";
//            String successUrl = "http://localhost:8082/api/payment/success";

            UserDto userDto = userService.getUserInfo().orElseThrow(() -> new AppExeption("User not found", "Paypal", HttpStatus.NOT_FOUND));
            Optional<CurrencyDto> currencyDto = currencyClient.getCurrencyById(Long.valueOf(paymentPaypalDto.currency()));
            if (currencyDto.isEmpty()) {
                throw new AppExeption("Currency not found", "Paypal", HttpStatus.NOT_FOUND);
            }

            CurrencyDto currency = currencyDto.get();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + userDto.getToken());

            ApiService apiService = new ApiService();
            String json = apiService.callExternalApi(currency.getCode());
            JSONObject jsonObject = new JSONObject(json);
            JSONObject ratesObject = jsonObject.getJSONArray("rates").getJSONObject(0);

            double mid = ratesObject.getDouble("mid");

            String successUrl = paymentPaypalDto.successUrl() + "?userId=" + userDto.getId() + "&JWTtoken=" + userDto.getToken() + "&currencyId=" + currency.getId() + "&total=" + paymentPaypalDto.total() + "&exchangeRate=" + mid;

            BigDecimal targetAmount = BigDecimal.valueOf(mid).multiply(BigDecimal.valueOf(paymentPaypalDto.total()));

            Payment payment = paypalService.createPayment(
                    targetAmount.doubleValue(),
                    "PLN",
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
            throw new AppExeption("Error occurred while processing payment", "Paypal", HttpStatus.EXPECTATION_FAILED);

        } catch (AppExeption e) {
            throw new AppExeption(e.getMessage(), "Paypal", e.getHttpStatus());
        }
        return ResponseEntity.ok(new RedirectView("/error"));
    }

    @PostMapping("/createPayout")
    public ResponseEntity<PayoutBatch> createPayout(@RequestBody PayoutRequestPaypalDto payoutRequestPaypalDto) throws PayPalRESTException, AppExeption {
        if (payoutRequestPaypalDto.total() <= 0) {
            throw new AppExeption("Amount must be greater than 0", "Paypal", HttpStatus.BAD_REQUEST);
        }
        try {

            UserDto userDto = userService.getUserInfo().orElseThrow(() -> new AppExeption("User not found", "Paypal", HttpStatus.NOT_FOUND));


            Optional<CurrencyDto> currency = currencyClient.getCurrencyById(Long.valueOf(payoutRequestPaypalDto.currencyId()));

            if (currency.isEmpty()) {
                throw new AppExeption("Currency not found", "Paypal", HttpStatus.NOT_FOUND);
            }

            CurrencyDto currencyDto = currency.get();
            ApiService apiService = new ApiService();
            String json = apiService.callExternalApi(currencyDto.getCode());
            JSONObject jsonObject = new JSONObject(json);
            JSONObject ratesObject = jsonObject.getJSONArray("rates").getJSONObject(0);

            double mid = ratesObject.getDouble("mid");
            BigDecimal total = BigDecimal.valueOf(payoutRequestPaypalDto.total()).multiply(BigDecimal.valueOf(mid));
            PayoutBatch payoutBatch = paypalService.createPayout(
                    payoutRequestPaypalDto.receiverEmail(),
                    total.doubleValue(),
                    "PLN"
            );
            logger.info("Create payout");

            paypalService.removeAmountToKantorAccount(payoutRequestPaypalDto.currencyId(), String.valueOf(userDto.getId()), payoutRequestPaypalDto.total());
            AddTransactionDto addTransactionDto = AddTransactionDto.builder()
                    .typeOfTransaction("PAYOUT")
                    .amountOfForeginCurrency(String.valueOf(payoutRequestPaypalDto.total()))
                    .ForeginCurrencyId(Long.valueOf(payoutRequestPaypalDto.currencyId()))
                    .targetCurrencyId(Long.valueOf(payoutRequestPaypalDto.currencyId()))
                    .targetCurrency(total.toString())
                    .appUserId(String.valueOf(userDto.getId()))
                    .exchangeRate(String.valueOf(mid))
                    .build();

            tranactionClient.addTranactionHistory(addTransactionDto);

            return ResponseEntity.ok(payoutBatch);
        } catch (AppExeption e) {
            throw new AppExeption(e.getMessage(), "Paypal", e.getHttpStatus());
        }
    }

    @GetMapping("/success")
    public RedirectView successPayment(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @RequestParam("userId") String userId, @RequestParam("currencyId") String currencyId, @RequestParam("total") String total, @RequestParam("exchangeRate") double mid) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);

            if (payment.getState().equals("approved")) {
                Optional<CurrencyDto> currency = currencyClient.getCurrencyById(Long.valueOf(currencyId));
                if (currency.isEmpty()) {
                    throw new AppExeption("Currency not found", "Paypal", HttpStatus.NOT_FOUND);
                }

                paypalService.addAmountToKantorAccount(payment, userId, currencyId, total);

                AddTransactionDto addTransactionDto = AddTransactionDto.builder()
                        .typeOfTransaction("BUY")
                        .amountOfForeginCurrency(payment.getTransactions().get(0).getAmount().getTotal())
                        .ForeginCurrencyId(63L)
                        .targetCurrencyId(Long.valueOf(currencyId))
                        .targetCurrency(total)
                        .appUserId(userId)
                        .exchangeRate(String.valueOf(mid))
                        .build();

                tranactionClient.addTranactionHistory(addTransactionDto);

                return new RedirectView("http://localhost:4200/currency-account");
            }
        } catch (PayPalRESTException e) {
            throw new AppExeption("Error occurred while processing payment", "Paypal", HttpStatus.EXPECTATION_FAILED);
        }
        return new RedirectView("http://localhost:8082/api/payment/error");
    }

    @PostMapping("/cancel")
    public RedirectView cancelPayment() {
        return new RedirectView("http://localhost:4200");
    }

    @PostMapping("/error")
    public RedirectView errorPayment() {
        return new RedirectView("http://localhost:4200");
    }

}
