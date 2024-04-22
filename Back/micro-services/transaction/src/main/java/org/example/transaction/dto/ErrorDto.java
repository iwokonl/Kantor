package org.example.transaction.dto;

public record ErrorDto(String message, String theNameOfThMicroservice) { // Niemutowalna klasa przechowująca dane błędu
}
