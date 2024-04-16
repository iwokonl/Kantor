package pl.kantor.backend.MVC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kantor.backend.MVC.model.ForeignCurrencyAccount;

import java.util.List;
import java.util.Optional;

public interface ForeignCurrencyAccountRepo extends JpaRepository<ForeignCurrencyAccount, Long> {
    List<ForeignCurrencyAccount> findAllByUserId(Long userId);
    Optional<ForeignCurrencyAccount> findByCurrencyCodeAndUserId(String currencyCode, Long userId);

    Optional<ForeignCurrencyAccount> findByUserIdAndCurrencyCode(Long id, String code);
}
