package org.example.paypal.service;

import org.springframework.web.reactive.function.client.WebClient;

public class ApiService {

    private WebClient webClient;

    public ApiService() {
        this.webClient = WebClient.create();
    }

    public String callExternalApi(String code) {
        String url = "https://api.nbp.pl/api/exchangerates/rates/a/"+ code +"/?format=json";
        String response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;
    }
}