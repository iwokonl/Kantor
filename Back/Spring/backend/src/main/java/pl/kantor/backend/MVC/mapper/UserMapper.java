package pl.kantor.backend.MVC.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import pl.kantor.backend.MVC.dto.SignUpDto;
import pl.kantor.backend.MVC.dto.UserDto;
import pl.kantor.backend.MVC.model.AppUser;

@Mapper(componentModel="spring")
@Component
public interface UserMapper { // Interfejs mapujący obiekty użytkownika na obiekty DTO
    UserDto toUserDto(AppUser appUser);

//  Ten mapping może się bugować ponieważ lombok ładuje się później niż mapstruct i mapstruck chce
//  pobrać pole password z klasy AppUser, które jest prywatne, a lombok nie generuje gettera dla tego pola w czasie
//  uruchomienia aplikacji. Więc dlatego trzeba dodać do pom.xml
    /*
        <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version> <!-- Upewnij się, że używasz odpowiedniej wersji -->
                <configuration>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <version>${lombok.version}</version>
                        </path>
                        <path>
                            <groupId>org.mapstruct</groupId>
                            <artifactId>mapstruct-processor</artifactId>
                            <version>1.5.5.Final</version> <!-- Zaktualizuj wersję MapStruct zgodnie z używaną w zależnościach -->
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>

        </plugins>

    </build>
     */
    @Mapping(target = "password", ignore = true)
    AppUser signUpToUser(SignUpDto signUpDto);


}
