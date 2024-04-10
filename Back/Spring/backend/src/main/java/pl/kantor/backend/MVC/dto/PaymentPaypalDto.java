package pl.kantor.backend.MVC.dto;

public record PaymentPaypalDto( Double total,
                                String currency,
                                String method,
                                String intent,
                                String description,
                                String cancelUrl,
                                String successUrl) { // Niemutowalna klasa przechowująca dane logowania
}
