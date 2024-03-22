package pl.kantor.backend.VMC.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.kantor.backend.VMC.dto.CurrencyDto;
import pl.kantor.backend.VMC.model.Currency;

import java.util.List;

@Mapper(componentModel="spring")
@Component
public interface CurrencyMapper {
    List<CurrencyDto> toCurrencyDto(List<Currency> currencies);
}
