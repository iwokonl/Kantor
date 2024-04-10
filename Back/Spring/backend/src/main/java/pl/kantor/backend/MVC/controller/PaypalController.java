package pl.kantor.backend.MVC.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import pl.kantor.backend.MVC.dto.PaymentPaypalDto;
import pl.kantor.backend.MVC.exeption.AppExeption;
import pl.kantor.backend.MVC.service.PaypalService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaypalController {

    private static final Logger log = LoggerFactory.getLogger(PaypalController.class);
    private final PaypalService paypalService;

    @PostMapping("/create")
    public ResponseEntity<RedirectView> createPayment(@RequestBody PaymentPaypalDto paymentPaypalDto) {
        try {
//            String cancelUrl = "http://localhost:8082/api/payment/cancel";
//            String successUrl = "http://localhost:8082/api/payment/success";
            Payment payment = paypalService.createPayment(
                    paymentPaypalDto.total(),
                    paymentPaypalDto.currency(),
                    paymentPaypalDto.method(),
                    paymentPaypalDto.intent(),
                    paymentPaypalDto.description(),
                    paymentPaypalDto.cancelUrl(),
                    paymentPaypalDto.successUrl()
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
    public RedirectView successPayment(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return new RedirectView("http://localhost:4200");
            }
        } catch (PayPalRESTException e) {
            throw new AppExeption("Error occurred while processing payment", HttpStatus.EXPECTATION_FAILED);
        }
        return new RedirectView("/error");
    }

    @PostMapping("/cancel")
    public RedirectView cancelPayment() {
        return new RedirectView("/cancel");
    }
}
