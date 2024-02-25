package pl.zeto.backend.VMC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zeto.backend.VMC.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByCode(String code);
}