package org.example.gateway.dto;

public record ErrorDto (String message, String theNameOfThMicroservice) { // Niemutowalna klasa przechowująca dane błędu
}
