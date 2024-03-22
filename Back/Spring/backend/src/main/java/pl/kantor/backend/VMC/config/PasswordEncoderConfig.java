package pl.kantor.backend.VMC.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { // Twożymy beana który będzie odpowiedzialny za szyfrowanie hasła
        return new BCryptPasswordEncoder();
    }
}