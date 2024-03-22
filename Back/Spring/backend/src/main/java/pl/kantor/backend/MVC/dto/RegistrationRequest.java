package pl.kantor.backend.MVC.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest { // Klasa przechowująca dane rejestracji
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}