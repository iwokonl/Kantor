package pl.kantor.backend.MVC.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.kantor.backend.MVC.dto.ForeignCurrencyAccountDto;
import pl.kantor.backend.MVC.model.ForeignCurrencyAccount;

import java.util.List;

@Mapper(componentModel="spring")
@Component
public interface ForeignCurrencyAccountMapper {
    List<ForeignCurrencyAccountDto> toForeignCurrencyAccountDto(List<ForeignCurrencyAccount> foreignCurrencyAccounts);
}