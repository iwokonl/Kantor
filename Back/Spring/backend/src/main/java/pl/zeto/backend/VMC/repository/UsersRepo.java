package pl.zeto.backend.VMC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zeto.backend.VMC.model.AppUser;

public interface UsersRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
