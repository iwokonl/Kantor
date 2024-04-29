package org.example.gateway.dto;

public record CredentialsDto(String username, char[] password) { // Niemutowalna klasa przechowująca dane logowania
}
