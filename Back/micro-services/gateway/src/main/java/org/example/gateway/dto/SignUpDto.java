package org.example.gateway.dto;

public record SignUpDto(String firstName, String lastName, String email, String username,
                        char[] password) {// Niemutowalna klasa przechowująca dane rejestracji

}
