package org.example.paypal.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PayoutBatch;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.example.paypal.dto.CurrencyDto;
import org.example.paypal.dto.PaymentPaypalDto;
import org.example.paypal.dto.PayoutRequestPaypalDto;
import org.example.paypal.dto.UserDto;
import org.example.paypal.exeption.AppExeption;
import org.example.paypal.feign.CurrencyClient;
import org.example.paypal.feign.UserClient;
import org.example.paypal.service.PaypalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.Optional;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/paypal")
public class PaypalController {

    private final PaypalService paypalService;
    private final UserClient userService;
    private final CurrencyClient currencyClient;
    private static final Logger logger = LoggerFactory.getLogger(PaypalController.class);

    @GetMapping("/test")
    public String test() {
        return "test";
    }
    @PostMapping("/create")
    public ResponseEntity<RedirectView> createPayment(@RequestBody PaymentPaypalDto paymentPaypalDto) {
        if(paymentPaypalDto.total() <= 0){
            throw new AppExeption("Amount must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        try {
//            String cancelUrl = "http://localhost:8082/api/payment/cancel";
//            String successUrl = "http://localhost:8082/api/payment/success";
            UserDto userDto = userService.getUserInfo().orElseThrow(() -> new AppExeption("User not found", HttpStatus.NOT_FOUND));
            Optional<CurrencyDto> currencyDto = currencyClient.getCurrencyById(Long.valueOf(paymentPaypalDto.currency()));
            if(currencyDto.isEmpty()){
                throw new AppExeption("Currency not found", HttpStatus.NOT_FOUND);
            }
            CurrencyDto currency = currencyDto.get();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + userDto.getToken());
            String successUrl = paymentPaypalDto.successUrl() + "?userId=" + userDto.getId() + "&JWTtoken=" + userDto.getToken() + "&currencyId=" + currency.getId();
            logger.info("Currency code: " + currency.getCode());
            Payment payment = paypalService.createPayment(
                    paymentPaypalDto.total(),
                    currency.getCode(),
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

        } catch (AppExeption e) {
            throw new AppExeption(e.getMessage(), e.getHttpStatus());
        }
        return ResponseEntity.ok(new RedirectView("/error"));
    }

    @PostMapping("/createPayout")
    public ResponseEntity<PayoutBatch> createPayout(@RequestBody PayoutRequestPaypalDto payoutRequestPaypalDto) throws PayPalRESTException, AppExeption {
        if (payoutRequestPaypalDto.total() <= 0) {
            throw new AppExeption("Amount must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        try {

            UserDto userDto = userService.getUserInfo().orElseThrow(() -> new AppExeption("User not found", HttpStatus.NOT_FOUND));

            paypalService.removeAmountToKantorAccount(payoutRequestPaypalDto.currency(), String.valueOf(userDto.getId()), payoutRequestPaypalDto.total());


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

    @GetMapping("/success")
    public RedirectView successPayment(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @RequestParam("userId") String userId, @RequestParam("currencyId") String currencyId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);

            if (payment.getState().equals("approved")) {
                paypalService.addAmountToKantorAccount(payment, userId,currencyId);
                logger.info("Payment approved asdasdasd");
                return new RedirectView("http://localhost:4200");
            }
        } catch (PayPalRESTException e) {
            throw new AppExeption("Error occurred while processing payment", HttpStatus.EXPECTATION_FAILED);
        }
        return new RedirectView("http://localhost:8082/api/payment/error");
    }

    @PostMapping("/cancel")
    public RedirectView cancelPayment() {
        return new RedirectView("http://localhost:4200/payment/cancel");
    }

    @PostMapping("/error")
    public RedirectView errorPayment() {
        return new RedirectView("http://localhost:4200/payment/error");
    }

}
