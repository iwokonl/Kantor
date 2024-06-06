package org.example.gateway.mapper;

import org.example.gateway.dto.SignUpDto;
import org.example.gateway.dto.UserDto;
import org.example.gateway.model.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface UserMapper { // Interfejs mapujący obiekty użytkownika na obiekty DTO
    UserDto toUserDto(AppUser appUser);

    @Mapping(target = "password", ignore = true)
    AppUser signUpToUser(SignUpDto signUpDto);


}
