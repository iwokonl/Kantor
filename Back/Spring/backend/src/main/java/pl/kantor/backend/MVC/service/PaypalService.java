package pl.kantor.backend.MVC.service;

import com.paypal.api.payments.*;
import com.paypal.api.payments.Currency;
import com.paypal.base.Constants;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Value("${paypal.client.id}")
    private String clientId;
    @Value("${paypal.client.secret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;

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

    public Payment transferMoney(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl,
            String payeeId,
            String peyerId

    ) throws PayPalRESTException {
        Details details = new Details();
        details.setShipping("0");
        details.setSubtotal("0");
        details.setTax("0");

        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.forLanguageTag(currency), "%.2f", total));
        amount.setDetails(details);

        Payee payee = new Payee();
        payee.setMerchantId(payeeId);

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setPayee(payee);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setEmail(peyerId); // Ustaw na adres e-mail konta PayPal płatnika
        payer.setPayerInfo(payerInfo);

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


    public APIContext getAPIContext() throws PayPalRESTException {
        OAuthTokenCredential tokenCredential = new OAuthTokenCredential(clientId, clientSecret, getPayPalSDKConfig());
        String accessToken = tokenCredential.getAccessToken();
        APIContext apiContext = new APIContext(accessToken);
        apiContext.setConfigurationMap(getPayPalSDKConfig());
        return apiContext;
    }

    private Map<String, String> getPayPalSDKConfig() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put(Constants.MODE, Constants.SANDBOX);
        configMap.put("oauth.EndPoint", "https://api.sandbox.paypal.com");
        configMap.put("service.EndPoint", "https://api.sandbox.paypal.com");

        return configMap;
    }

    //TODO: Usunąc opłatę za przelew bo 2% ściąga z konta a tak być nie może
    public PayoutBatch createPayout(String receiverEmail, Double total, String currency) {
        PayoutSenderBatchHeader senderBatchHeader = new PayoutSenderBatchHeader();

        senderBatchHeader.setSenderBatchId(new Random().nextInt(99999) + "").setEmailSubject("You have a payment");
        PayoutItem item = new PayoutItem();

        item.setRecipientType("EMAIL").setAmount(new Currency(currency, String.format(Locale.US, "%.2f", total))).setReceiver(receiverEmail)
                .setSenderItemId("item_" + new Random().nextInt(99999)).setNote("Thank you.");

        List<PayoutItem> items = new ArrayList<PayoutItem>();
        items.add(item);

        Payout payout = new Payout();

        payout.setSenderBatchHeader(senderBatchHeader).setItems(items);
        PayoutBatch response = null;

        try {
            APIContext context = getAPIContext();
            Map<String, String> configMap = new HashMap<>();
            configMap.put("mode", mode);

            // Dodaj tutaj dodatkowe parametry konfiguracyjne, jeśli są potrzebne
            response = payout.create(context, configMap);
        } catch (Exception e) {
            logger.error("Error occurred while creating payout: " + e.getMessage(), e);
            throw new AppExeption("Error occurred while creating payout", HttpStatus.EXPECTATION_FAILED);
        }

        return response;
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
        Optional<ForeignCurrencyAccount> account = foreignCurrencyAccountRepo.findByCurrencyCodeAndUserId(payment.getTransactions().get(0).getAmount().getCurrency(), Long.parseLong(userId));
        if (account.isPresent()) {
            ForeignCurrencyAccount foreignCurrencyAccount = account.get();
            foreignCurrencyAccount.setBalance(foreignCurrencyAccount.getBalance().add(new BigDecimal(payment.getTransactions().get(0).getAmount().getTotal())));
            foreignCurrencyAccountRepo.save(foreignCurrencyAccount);
        } else {
            throw new AppExeption("Account does not exist", HttpStatus.NOT_FOUND);
        }

    }

    public void removeAmountToKantorAccount(String currency, String userId, Double total) throws AppExeption {

        Optional<ForeignCurrencyAccount> account = foreignCurrencyAccountRepo.findByCurrencyCodeAndUserId(currency, Long.parseLong(userId));
        if (account.isPresent()) {
            ForeignCurrencyAccount foreignCurrencyAccount = account.get();
            if (foreignCurrencyAccount.getBalance().subtract(BigDecimal.valueOf(total)).compareTo(BigDecimal.ZERO) < 0) {
                throw new AppExeption("Not enough money on account", HttpStatus.BAD_REQUEST);
            }

            foreignCurrencyAccount.setBalance(foreignCurrencyAccount.getBalance().subtract(BigDecimal.valueOf(total)));
            foreignCurrencyAccountRepo.save(foreignCurrencyAccount);
        } else {
            throw new AppExeption("Account does not exist", HttpStatus.NOT_FOUND);
        }

    }
}
