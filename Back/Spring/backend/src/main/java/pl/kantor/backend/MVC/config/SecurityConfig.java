package pl.kantor.backend.MVC.config;

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
                        request.requestMatchers(HttpMethod.POST,  "authorization/register","/error", "authorization/login"
                                        , "currency/search" , "messages").permitAll() // Pozwala na wykonywanie zapytań POST na adresach: /login, /register
                                .requestMatchers(HttpMethod.GET, "test","api/v1/auth/**","v2/api-docs","v3/api-docs","v3/api-docs/**","swagger-resources","swagger-resources/**","configuration/ui","configuration/security","swagger-ui/**","webjars/**","/swagger-ui.html").permitAll() // Pozwala na wykonywanie zapytań GET na adresach: /search/**
                                .anyRequest().authenticated()); // Wymaga autoryzacji dla pozostałych zapytań
        return httpSecurity.build();
    }
    //    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        configurer.addPathPrefix("/api", c -> true);
//    }
}
