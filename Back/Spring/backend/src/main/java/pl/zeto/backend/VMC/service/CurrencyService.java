package pl.zeto.backend.VMC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zeto.backend.VMC.model.Currency;
import pl.zeto.backend.VMC.model.CurrencyResponse;
import pl.zeto.backend.VMC.repository.CurrencyRepository;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private NbpApiClient nbpApiClient;

    public void updateCurrencyRate(String currencyCode) {
        CurrencyResponse response = nbpApiClient.getCurrencyRate(currencyCode);

        if (response != null) {
            Currency currency = currencyRepository.findByCode(currencyCode);

            if (currency == null) {
                currency = new Currency();
                currency.setCode(currencyCode);
            }

            currency.setRate(response.getMid());

            currencyRepository.save(currency);
        }
    }

    public CurrencyResponse getCurrencyRate(String currencyCode) {
        return nbpApiClient.getCurrencyRate(currencyCode);
    }
}