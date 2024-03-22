package pl.kantor.backend.MVC.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.kantor.backend.MVC.dto.CurrencyDto;
import pl.kantor.backend.MVC.model.Currency;

import java.util.List;

@Mapper(componentModel="spring")
@Component
public interface CurrencyMapper {
    List<CurrencyDto> toCurrencyDto(List<Currency> currencies);
}
