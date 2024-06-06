package org.example.currencies.service;

import lombok.RequiredArgsConstructor;
import org.example.currencies.dto.CurrencyDto;
import org.example.currencies.dto.SearchCurrencyDto;
import org.example.currencies.exeption.AppExeption;
import org.example.currencies.mapper.CurrencyMapper;
import org.example.currencies.model.Currency;
import org.example.currencies.repository.CurrencyRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepo currencyRepository;
    private final CurrencyMapper currencyMapper;

    public List<CurrencyDto> findByName(SearchCurrencyDto query) {
        if (query.name() == null || query.name().isEmpty()) {
            return currencyMapper.toCurrencyDto(new ArrayList<>());
        }
        Optional<List<Currency>> resultsNameOptional = currencyRepository.findByNameContainingIgnoreCase(query.name().toLowerCase());
        Optional<List<Currency>> resultsCodeOptional = currencyRepository.findByCodeStartingWith(query.name().toUpperCase());

        Optional<List<Currency>> combinedOptional = resultsNameOptional.map(ArrayList::new) // Tworzy nową ArrayList na podstawie listy z resultsNameOptional
                .map(list -> {
                    resultsCodeOptional.ifPresent(list::addAll); // Dodaje wszystkie elementy z listy z resultsCodeOptional
                    return list; // Zwraca połączoną listę
                });

        if (combinedOptional.isEmpty()) {
            throw new AppExeption("Currency not found", "currencies", HttpStatus.NOT_FOUND);
        }
        List<Currency> results = combinedOptional.get();
        if (results.isEmpty()) {
            throw new AppExeption("Currency not found", "currencies", HttpStatus.NOT_FOUND);
        }

        List<Currency> sortedResults = results.stream()
                .sorted(Comparator.comparing(Currency::getName))
                .distinct()
                .collect(Collectors.toList());
        return currencyMapper.toCurrencyDto(sortedResults);
    }

    public CurrencyDto findById(Long id) {
        Optional<Currency> currency = currencyRepository.findById(id);
        if (currency.isEmpty()) {
            throw new AppExeption("Currency not found", "currencies", HttpStatus.NOT_FOUND);
        }
        return currencyMapper.toCurrencyDto(currency.get());
    }

}
