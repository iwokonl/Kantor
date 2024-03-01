package pl.zeto.backend.VMC.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.zeto.backend.VMC.model.Role;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDto {
    private Long id;
    private Role role;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String token;

}
