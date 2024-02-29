package pl.zeto.backend.VMC.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.zeto.backend.VMC.service.CustomUserDetailsService;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;


@Configuration
@EnableWebMvc
@EnableWebSecurity

public class SecurityConfig {
    @Autowired
    @Qualifier("userService")
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Bean
    public FilterRegistrationBean crossFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Pozwala pobierać dane zawierające informacje uwierzytelniające
        config.addAllowedOrigin("http://localhost:4200"); // URL frontendu

        config.addAllowedHeader(HttpHeaders.AUTHORIZATION);
        config.addAllowedHeader(HttpHeaders.CONTENT_TYPE);
        config.addAllowedHeader(HttpHeaders.ACCEPT);

        config.addAllowedMethod(HttpMethod.GET.name());
        config.addAllowedMethod(HttpMethod.POST.name());
        config.addAllowedMethod(HttpMethod.PUT.name());
        config.addAllowedMethod(HttpMethod.DELETE.name());

        config.setMaxAge(3600L); // Czas trwania sesji
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(-102);
        return bean;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/messages","/", "/home","/register_success","redirect:/register_success","/redirect:register", "/register", "/process_register", "/register?error=userexists").permitAll() // Dostęp bez autoryzacji
                        .anyRequest().authenticated() // Wszystkie inne żądania wymagają autoryzacji
                )
                .formLogin(login -> login
                        .loginPage("/login") // Określa stronę logowania
                        .permitAll()
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );
        return http.build();
    }
}