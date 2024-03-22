package pl.kantor.backend.MVC.dto;

public record SignUpDto (String firstName, String lastName,String email,String username, char[] password){// Niemutowalna klasa przechowująca dane rejestracji

}
