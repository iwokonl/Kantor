package pl.zeto.backend.VMC.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.zeto.backend.VMC.dto.CurrencyDto;
import pl.zeto.backend.VMC.exeption.AppExeption;
import pl.zeto.backend.VMC.mapper.CurrencyMapper;
import pl.zeto.backend.VMC.model.Currency;
import pl.zeto.backend.VMC.repository.CurrencyRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepo  currencyRepository;
    private final CurrencyMapper currencyMapper;
    public List<CurrencyDto> findByName(String query) {
        Optional<List<Currency>> results = currencyRepository.findByCodeOrNameStartingWith(query.toUpperCase(), query.toLowerCase());
        if (results.isEmpty()) {
            throw new AppExeption("Currency not found", HttpStatus.NOT_FOUND);
        }
        List<CurrencyDto> currencies = currencyMapper.toCurrencyDto(results.get());
        return currencies;
    }

}
