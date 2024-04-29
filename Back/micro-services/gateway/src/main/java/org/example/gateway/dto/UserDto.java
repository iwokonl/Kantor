package org.example.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.gateway.model.Role;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDto { // Klasa przechowująca dane użytkownika
    private Long id;
    private Role role;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String token;

}
