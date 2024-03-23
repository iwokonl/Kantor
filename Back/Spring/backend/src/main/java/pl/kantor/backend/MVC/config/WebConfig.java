package pl.kantor.backend.MVC.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.filter.CorsFilter;

import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean crosFilter() { // Rejestracja filtra zabezpieczeń CORS
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // Konfiguracja zabezpieczeń CORS
        CorsConfiguration config = new CorsConfiguration(); // Konfiguracja zabezpieczeń CORS
        config.setAllowCredentials(true); // Zezwala na wykonywanie zapytań z poziomu przeglądarki
        config.addAllowedOrigin("http://localhost:4200"); // Zezwala na wykonywanie zapytań z adresu: http://localhost:4200
        config.setAllowedHeaders(Arrays.asList( // Zezwala na wykonywanie zapytań z nagłówkami typu: AUTHORIZATION, CONTENT_TYPE, ACCEPT
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT

        ));
        config.setAllowedMethods(Arrays.asList( // Zezwala na wykonywanie zapytań typu GET, POST, PUT, DELETE
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()
        ));
        config.setMaxAge(3600L); // Ustawia czas życia zapytania na 3600 sekund
        source.registerCorsConfiguration("/**", config); // Rejestruje konfigurację zabezpieczeń CORS dla wszystkich adresów
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source)); // Rejestruje filtr zabezpieczeń CORS
        bean.setOrder(-102); // Ustawia priorytet filtra na -102 (Aby wykonał się ostatni)
        return bean;
    }


    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", c -> true);
    }
}
