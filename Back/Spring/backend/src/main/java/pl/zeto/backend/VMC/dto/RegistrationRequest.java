package pl.zeto.backend.VMC.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    // Getters and setters
}