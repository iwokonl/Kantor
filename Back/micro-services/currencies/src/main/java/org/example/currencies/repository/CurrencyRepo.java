package org.example.currencies.repository;

import org.example.currencies.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface CurrencyRepo extends JpaRepository<Currency, Long> {

    Optional<List<Currency>> findByCodeStartingWith(String code);
    Optional<List<Currency>> findByNameStartingWith(String name);
    Optional<Currency> findByName(String name);
    Optional<Currency> findByCode(String code);

    @Query("SELECT c FROM Currency c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Optional<List<Currency>> findByNameContainingIgnoreCase(@Param("name") String name);
}
