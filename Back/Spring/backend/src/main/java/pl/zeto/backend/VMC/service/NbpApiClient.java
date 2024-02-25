package pl.zeto.backend.VMC.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.zeto.backend.VMC.model.CurrencyResponse;

@Service
public class NbpApiClient {

    private final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/A/";

    public CurrencyResponse getCurrencyRate(String currencyCode) {
        RestTemplate restTemplate = new RestTemplate();
        String url = NBP_API_URL + currencyCode + "/?format=json";
        return restTemplate.getForObject(url, CurrencyResponse.class);
    }
}