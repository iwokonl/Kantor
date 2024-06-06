package org.example.currencies.mapper;

import org.example.currencies.dto.CurrencyDto;
import org.example.currencies.model.Currency;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface CurrencyMapper {
    List<CurrencyDto> toCurrencyDto(List<Currency> currencies);

    CurrencyDto toCurrencyDto(Currency currencies);
}
