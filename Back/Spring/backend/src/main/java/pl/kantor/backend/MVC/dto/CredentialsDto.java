package pl.kantor.backend.MVC.dto;

public record CredentialsDto(String username, char[] password) { // Niemutowalna klasa przechowująca dane logowania
}
