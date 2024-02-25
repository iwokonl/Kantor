package pl.zeto.backend.VMC.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Entity
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;

    // Konstruktor bezargumentowy potrzebny dla JPA
    public AppUser() {
    }

    // Konstruktor z wszystkimi polami, może być użyteczny w logice biznesowej
    public AppUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Gettery i Settery są generowane przez Lombok, ale możesz je również dodać ręcznie
}