package pl.kantor.backend.VMC.dto;

public record CredentialsDto(String username, char[] password) { // Niemutowalna klasa przechowująca dane logowania
}
