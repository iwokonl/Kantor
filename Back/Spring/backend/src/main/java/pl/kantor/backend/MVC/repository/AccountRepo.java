package pl.kantor.backend.MVC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kantor.backend.MVC.model.ForeignCurrencyAccount;

public interface AccountRepo extends JpaRepository<ForeignCurrencyAccount, Long> {

}
