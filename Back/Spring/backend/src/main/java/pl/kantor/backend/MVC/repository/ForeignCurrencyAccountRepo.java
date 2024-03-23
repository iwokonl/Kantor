package pl.kantor.backend.MVC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kantor.backend.MVC.model.ForeignCurrencyAccount;

import java.util.Optional;

public interface ForeignCurrencyAccountRepo extends JpaRepository<ForeignCurrencyAccount, Long> {

    Optional<ForeignCurrencyAccount> findById(Long id);

}
