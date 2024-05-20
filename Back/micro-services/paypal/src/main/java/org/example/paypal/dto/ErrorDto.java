package org.example.paypal.dto;

public record ErrorDto(String message, String theNameOfThMicroservice) { // Niemutowalna klasa przechowująca dane błędu
}
