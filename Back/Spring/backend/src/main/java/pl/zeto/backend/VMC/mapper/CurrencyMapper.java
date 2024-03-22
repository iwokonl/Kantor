package pl.zeto.backend.VMC.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.zeto.backend.VMC.dto.CurrencyDto;
import pl.zeto.backend.VMC.dto.SearchCurrencyDto;
import pl.zeto.backend.VMC.dto.UserDto;
import pl.zeto.backend.VMC.model.AppUser;
import pl.zeto.backend.VMC.model.Currency;

import java.util.List;

@Mapper(componentModel="spring")
@Component
public interface CurrencyMapper {
    List<CurrencyDto> toCurrencyDto(List<Currency> currencies);
}
