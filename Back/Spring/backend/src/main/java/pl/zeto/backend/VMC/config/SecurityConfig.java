package pl.zeto.backend.VMC.config;

import jakarta.persistence.Basic;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserAuthProvider userAuthProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { // Konfiguracja zabezpieczeń

        httpSecurity.csrf(AbstractHttpConfigurer::disable) // Ochrona CSRF wyłączona
                .addFilterBefore(new JwtAuthFilter(userAuthProvider), BasicAuthenticationFilter.class) // Dodanie filtra autoryzacji
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Wyłączenie zarządzania sesją
                .authorizeHttpRequests(request -> // Konfiguracja zabezpieczeń
                        request.requestMatchers(HttpMethod.POST, "/login", "/register").permitAll() // Pozwala na wykonywanie zapytań POST na adresach: /login, /register
                                .anyRequest().authenticated()); // Wymaga autoryzacji dla pozostałych zapytań
        return httpSecurity.build();
    }
}
