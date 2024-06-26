package pl.kantor.backend.MVC.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import pl.kantor.backend.MVC.dto.ForeignCurrencyAccountDto;
import pl.kantor.backend.MVC.model.ForeignCurrencyAccount;

import java.util.List;

@Mapper(componentModel="spring")
@Component
public interface ForeignCurrencyAccountMapper {

//    TODO: Zadać pytanie jak działa mapowanie w mapstruct - Iwo
    List<ForeignCurrencyAccountDto> toForeignCurrencyAccountDto(List<ForeignCurrencyAccount> foreignCurrencyAccounts);
//    ForeignCurrencyAccountDto toForeignCurrencyAccountDto(ForeignCurrencyAccount foreignCurrencyAccounts);
    @Mapping(target = "curencyCode", source = "currency.code")
    @Mapping(target = "curencyName", source = "currency.name")
    @Mapping(target = "userId", source = "user.id")
    ForeignCurrencyAccountDto ForeignCurrencyAccounttoForeignCurrencyAccountDto(ForeignCurrencyAccount foreignCurrencyAccount);
//    ForeignCurrencyAccount toForeignCurrencyAccount(ForeignCurrencyAccountDto foreignCurrencyAccountDto);
}
