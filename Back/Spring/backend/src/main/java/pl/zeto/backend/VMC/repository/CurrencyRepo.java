package pl.zeto.backend.VMC.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import pl.zeto.backend.VMC.model.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepo extends JpaRepository<Currency, Long> {

    Optional<List<Currency>> findByNameStartingWith(String query);
    Optional<Currency> findByName(String name);

}
