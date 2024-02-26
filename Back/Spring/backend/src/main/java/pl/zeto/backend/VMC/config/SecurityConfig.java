package pl.zeto.backend.VMC.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Wyłącza ochronę CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Pozwala na dostęp do wszystkich zasobów bez autoryzacji
                );
        return http.build();
    }
}