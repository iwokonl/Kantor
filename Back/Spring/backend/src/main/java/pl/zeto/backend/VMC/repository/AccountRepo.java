package pl.zeto.backend.VMC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zeto.backend.VMC.model.AppAccount;

public interface AccountRepo extends JpaRepository<AppAccount, Long> {

}
