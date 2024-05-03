Jeśli tworzysz nowy serwis to dodaj: config client, eureka discovery client, spring boot actuator

https://www.youtube.com/watch?v=KJ0cSvYj41c&t=3407s
Róbcie z tego bo amen XD

## Komunikacja między mikrousługami

Jeśli chcesz aby mikrousługi komunikowały się ze sobą, musisz dodać odpowiednie zależności i konfiguracje do każdej z
nich.

Dodaj `FeignConfig` do katalogu `config` aby mikrousługa miała możliwość autoryzować się w bramie domyślnej
Działa to na takiej zasadzie że mikrousługa wysyła zapytanie do bramy z tokenem który jest pobierany z hedera
odtrzymanego z `gateway`, a brama sprawdza czy token jest poprawny i czy mikrousługa ma dostęp do zasobu.

```java
@RequiredArgsConstructor
@Configuration
public class FeignConfig {

    @Autowired
    private final ForeignCurrencyAccountService foreignCurrencyAccountService;

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = foreignCurrencyAccountService.getToken();
            if (token != null) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}
```

W serwisie mikrousługi dodaj metodę `getToken`. Pozwoli ona na pobieranie z hedera wysyłanego przez `gateway` tokenu
JWT:

```java
    public String getToken() {
    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String jwtToken = authHeader.substring(7); // Odcinamy "Bearer " aby uzyskać sam token
        return jwtToken;
    }
    return null;
}
```

Dodaj zależności do `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

Dodaj adnotację `@EnableFeignClients` do klasy głównej aplikacji z której wychodzi zapytanie do innej mikrousługi:

```java
@EnableFeignClients
@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

}
```
Dodaj adnotację `@FeignClient` do interfejsu, który będzie używany do komunikacji z inną mikrousługą. Zmienna `name` musi być taka sama jak nazwa aplikacji w pliku konfiguracyjnym `application.yml` w mikrousłudze z którą chcesz się połączyć. Zmienna `url` to adres mikrousługi do której chcesz się połączyć. W przykładzie poniżej jest to `http://localhost:8073/api`

```java
@FeignClient(name = "example-service", url = "http://localhost:8073/api")
public interface CurrencyClient {
    @GetMapping("/v1/example/id/{id}")
    Optional<CurrencyDto> getexampleById(@PathVariable("id") Long id);
}
```

## Kod Security dla mikrousług

Poniżej znajdują się dwie klasy które trzeba dodać jeśli chcesz aby kominikacja odbywała się tylko między gateway i
mikroserosami a nie mikroserwis i świat zewnętrzny. Zmienić tylko adres `127.0.0.1` na odpowiedni dla ciebie. Jeśli
używasz na localhost zostaw jak jest.

Konfiguracja klasy `SecurityConfig`:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // Ochrona CSRF wyłączona
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Wyłączenie zarządzania sesją
                .authorizeHttpRequests(request -> // Konfiguracja zabezpieczeń
                        request.requestMatchers("/**").access(new IpAuthorizationManager("127.0.0.1")) // Te ip zmienić na inne jeśli nie uruchamiasz tego lokalnie
                                .anyRequest().authenticated()); // Wymaga autoryzacji dla pozostałych zapytań

        return httpSecurity.build();
    }
}
```

Konfiguracja klasy `IpAuthorizationManager`:

```java
@RequiredArgsConstructor
public class IpAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final String gatewayIp;


    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        String requestIp = request.getRemoteAddr();
        boolean granted = gatewayIp.equals(requestIp);
        return new AuthorizationDecision(granted);
    }
}
```

# Projekt Mikrousług

Ten projekt to zestaw mikrousług zbudowanych przy użyciu Java, Spring Boot i Maven.

## Usługi

Projekt składa się z następujących usług:

1. **CurrenciesApplication**: Usługa zarządzająca walutami.
2. **DiscoveryApplication**: Usługa odkrywania, która pomaga w komunikacji między mikrousługami.

## Wymagania

- Java 8 lub nowsza
- Docker
- Maven

## Uruchomienie projektu

1. Sklonuj repozytorium na swoje lokalne środowisko.
2. Uruchom Docker Compose, aby uruchomić wymagane usługi, takie jak PostgreSQL, pgAdmin i Zipkin:

    ```bash
    cd _docker
    docker-compose up -d
    ```

3. Uruchom każdą usługę za pomocą Maven:

    ```bash
    cd ../currencies
    mvn spring-boot:run

    cd ../discovery-server
    mvn spring-boot:run
    ```

## Testowanie

Każda usługa ma zestaw testów jednostkowych, które można uruchomić za pomocą Maven:

    ```bash
    mvn test
    ```

## Dokumentacja

Więcej informacji na temat konfiguracji i korzystania z tych usług można znaleźć w dokumentacji każdej usługi.

## Wsparcie

Jeśli masz jakiekolwiek pytania lub problemy, prosimy o kontakt.

## Licencja

Ten projekt jest dostępny na licencji [wstaw tutaj nazwę licencji].