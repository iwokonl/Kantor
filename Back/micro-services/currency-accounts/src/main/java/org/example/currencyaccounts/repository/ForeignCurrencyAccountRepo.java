package org.example.currencyaccounts.repository;

import org.example.currencyaccounts.model.ForeignCurrencyAccount;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ForeignCurrencyAccountRepo extends JpaRepository<ForeignCurrencyAccount, Long> {
    Optional<ForeignCurrencyAccount> findByUserIdAndCurrencyId(Long id, Long CurrencyId);

    Optional<ForeignCurrencyAccount> findByCurrencyIdAndUserId(Long currencyId, Long userId);

    List<ForeignCurrencyAccount> findAllByUserId(Long id);

    Optional<ForeignCurrencyAccount> findByUserIdAndId(Long id, Long id1);
}
