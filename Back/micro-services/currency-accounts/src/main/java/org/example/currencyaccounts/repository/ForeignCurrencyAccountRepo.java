package org.example.currencyaccounts.repository;

import org.example.currencyaccounts.model.ForeignCurrencyAccount;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ForeignCurrencyAccountRepo extends JpaRepository<ForeignCurrencyAccount, Long> {
    List<ForeignCurrencyAccount> findAllByUserId(Long userId);
    Optional<ForeignCurrencyAccount> findByCurrencyCodeAndUserId(String currencyCode, Long userId);
    void deleteById(Long Id);
    Optional<ForeignCurrencyAccount> findByUserIdAndCurrencyCode(Long id, String code);
}
