package org.example.currencyaccounts.mapper;

import org.example.currencyaccounts.dto.ForeignCurrencyAccountDto;
import org.example.currencyaccounts.model.ForeignCurrencyAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ForeignCurrencyAccountMapper {

//    TODO: Zadać pytanie jak działa mapowanie w mapstruct - Iwo

    List<ForeignCurrencyAccountDto> toForeignCurrencyAccountDtoList(List<ForeignCurrencyAccount> accounts);

    ForeignCurrencyAccountDto toForeignCurrencyAccountDto(ForeignCurrencyAccount foreignCurrencyAccount);

    ForeignCurrencyAccount toForeignCurrencyAccount(ForeignCurrencyAccountDto foreignCurrencyAccountDto);
}
