package org.example.paypal.dto;

public record PayoutRequestPaypalDto(String receiverEmail, Double total, String currency){

}
