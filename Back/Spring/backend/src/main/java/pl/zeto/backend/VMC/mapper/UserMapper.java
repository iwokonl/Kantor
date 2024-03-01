package pl.zeto.backend.VMC.mapper;

import org.hibernate.annotations.Comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import pl.zeto.backend.VMC.dto.SignUpDto;
import pl.zeto.backend.VMC.dto.UserDto;
import pl.zeto.backend.VMC.model.AppUser;
import pl.zeto.backend.VMC.service.UserService;

@Mapper(componentModel="spring", uses= UserService.class)
@Component
public interface UserMapper {
    UserDto toUserDto(AppUser appUser);
    @Mapping(target = "password", ignore = true)
    AppUser signUpToUserr(SignUpDto signUpDto);
}
