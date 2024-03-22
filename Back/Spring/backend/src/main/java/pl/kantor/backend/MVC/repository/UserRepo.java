package pl.kantor.backend.MVC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kantor.backend.MVC.model.AppUser;

import java.util.Optional;

public interface UserRepo extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

}