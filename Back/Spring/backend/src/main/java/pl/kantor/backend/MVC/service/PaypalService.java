package pl.kantor.backend.MVC.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.reactive.function.client.WebClient;
import pl.kantor.backend.MVC.config.UserAuthProvider;
import pl.kantor.backend.MVC.exeption.AppExeption;
import pl.kantor.backend.MVC.model.ForeignCurrencyAccount;
import pl.kantor.backend.MVC.repository.ForeignCurrencyAccountRepo;


import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaypalService {

    private final APIContext apiContext;
    private final ForeignCurrencyAccountRepo foreignCurrencyAccountRepo;
    private final UserAuthProvider userAuthProvider;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(PaypalService.class);
    private final WebClient webClient;
    public Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl
    ) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.forLanguageTag(currency), "%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(
            String paymentId,
            String payerId)
            throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecute);
    }

    public void addAmountToKantorAccount(Payment payment, String userId) {
        Optional<ForeignCurrencyAccount> account = foreignCurrencyAccountRepo.findByCurrencyCodeAndUserId(payment.getTransactions().get(0).getAmount().getCurrency(),Long.parseLong(userId));
        if (account.isPresent()) {
            ForeignCurrencyAccount foreignCurrencyAccount = account.get();
            foreignCurrencyAccount.setBalance(foreignCurrencyAccount.getBalance().add(new BigDecimal(payment.getTransactions().get(0).getAmount().getTotal())));
            foreignCurrencyAccountRepo.save(foreignCurrencyAccount);
        }
        else {
            throw new AppExeption("Account does not exist", HttpStatus.NOT_FOUND);
        }

    }
}
