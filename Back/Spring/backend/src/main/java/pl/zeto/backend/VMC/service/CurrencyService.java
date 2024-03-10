package pl.zeto.backend.VMC.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.zeto.backend.VMC.dto.CurrencyDto;
import pl.zeto.backend.VMC.dto.SearchCurrencyDto;
import pl.zeto.backend.VMC.exeption.AppExeption;
import pl.zeto.backend.VMC.mapper.CurrencyMapper;
import pl.zeto.backend.VMC.model.Currency;
import pl.zeto.backend.VMC.repository.CurrencyRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepo  currencyRepository;
    private final CurrencyMapper currencyMapper;
    public List<CurrencyDto> findByName(SearchCurrencyDto query) {
        Optional<List<Currency>> resultsNameOptional = currencyRepository.findByNameStartingWith(query.name().toLowerCase());
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

        return currencyMapper.toCurrencyDto(results);
    }

}
