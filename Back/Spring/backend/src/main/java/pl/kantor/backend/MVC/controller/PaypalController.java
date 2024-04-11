package pl.kantor.backend.MVC.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import pl.kantor.backend.MVC.config.UserAuthProvider;
import pl.kantor.backend.MVC.dto.PaymentPaypalDto;
import pl.kantor.backend.MVC.exeption.AppExeption;
import pl.kantor.backend.MVC.service.PaypalService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.auth0.jwt.JWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kantor.backend.MVC.service.UserService;

import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaypalController {


    private final PaypalService paypalService;
    private final UserAuthProvider userAuthProvider;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(PaypalController.class);
    @PostMapping("/create")
    public ResponseEntity<RedirectView> createPayment(@RequestBody PaymentPaypalDto paymentPaypalDto) {
        try {
//            String cancelUrl = "http://localhost:8082/api/payment/cancel";
//            String successUrl = "http://localhost:8082/api/payment/success";

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            Map<String,String> response = userService.jwtInfo(currentUserName);
            String userId = response.get("id");
//            TODO:Zapytać się czemu to działa z znakiem "?" a nie z "&" w linku jeśli w linku paypala jest "&"
            String successUrl = paymentPaypalDto.successUrl() + "?userId=" + userId;
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
                    return ResponseEntity.ok(new RedirectView(links.getHref()));
                }
            }
        } catch (PayPalRESTException e) {
            throw new AppExeption("Error occurred while processing payment", HttpStatus.EXPECTATION_FAILED);

        }
        return ResponseEntity.ok(new RedirectView("/error"));
    }

    @GetMapping("/success")
    public RedirectView successPayment(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @RequestParam("userId") String userId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                paypalService.addAmountToKantorAccount(payment,userId);
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
