package pl.kantor.backend.VMC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kantor.backend.VMC.model.ForeignCurrencyAccount;

public interface AccountRepo extends JpaRepository<ForeignCurrencyAccount, Long> {

}
