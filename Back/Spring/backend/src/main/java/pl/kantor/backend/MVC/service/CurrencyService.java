package pl.kantor.backend.MVC.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.kantor.backend.MVC.exeption.AppExeption;
import pl.kantor.backend.MVC.mapper.CurrencyMapper;
import pl.kantor.backend.MVC.dto.CurrencyDto;
import pl.kantor.backend.MVC.dto.SearchCurrencyDto;
import pl.kantor.backend.MVC.model.Currency;
import pl.kantor.backend.MVC.repository.CurrencyRepo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepo  currencyRepository;
    private final CurrencyMapper currencyMapper;
    public List<CurrencyDto> findByName(SearchCurrencyDto query) {
        Optional<List<Currency>> resultsNameOptional = currencyRepository.findByNameContainingIgnoreCase(query.name().toLowerCase());
        Optional<List<Currency>> resultsCodeOptional = currencyRepository.findByCodeStartingWith(query.name().toUpperCase());

        Optional<List<Currency>> combinedOptional = resultsNameOptional.map(ArrayList::new) // Tworzy nową ArrayList na podstawie listy z resultsNameOptional
                .map(list -> {
                    resultsCodeOptional.ifPresent(list::addAll); // Dodaje wszystkie elementy z listy z resultsCodeOptional
                    return list; // Zwraca połączoną listę
                });

        if (combinedOptional.isEmpty()) {
            throw new AppExeption("Currency not found", HttpStatus.NOT_FOUND);
        }
        List<Currency> results = combinedOptional.get();
        if (results.isEmpty()) {
            throw new AppExeption("Currency not found", HttpStatus.NOT_FOUND);
        }

        List<Currency> sortedResults = results.stream()
                .sorted(Comparator.comparing(Currency::getName))
                .distinct()
                .collect(Collectors.toList());
        return currencyMapper.toCurrencyDto(sortedResults);
    }

}
