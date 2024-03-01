package pl.zeto.backend.VMC.dto;

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