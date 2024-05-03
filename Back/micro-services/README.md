Jeśli tworzysz nowy serwis to dodaj: config client, eureka discovery client, spring boot actuator

https://www.youtube.com/watch?v=KJ0cSvYj41c&t=3407s
Róbcie z tego bo amen XD

# Podstawowe serwisy dla mikrousług

`Gateway` - serwis odpowiedzialny za przekierowywanie zapytań do odpowiednich mikrousług.

`Discovery` - serwis odpowiedzialny za odkrywanie mikrousług.

`Config` - serwis odpowiedzialny za konfigurację mikrousług.

### Konfiguracja serwisu `Config`
#### Dependency

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
</dependencies>
```
#### Config w `config-server`

```yaml
server:
  port: 8888

spring:
  profiles:
    active: native
  application:
    name: config-server
  cloud:
    config:
      server:
        native:
          search-locations: classpath:/configurations # Lokalizacja plików konfiguracyjnych
```

### Konfiguracja serwisu `Discovery`

#### Dependency

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
    
   <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    
   <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
</dependencies>
```

#### Config w `config-server`

```yaml
spring:
  application:
    name: discovery-server

eureka:
  instance:
    hostname: localhost # Nazwa hosta
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ # Eureka server URL

server:
  port: 8761

```

#### Config w `Discovery`

```yaml
spring:
  application:
    name: discovery-server
  config:
    import: optional:configserver:http://localhost:8888

```

### Konfiguracja serwisu `Gateway`

#### Dependency

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    
   <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
    
   <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway-mvc</artifactId>
    </dependency>
   
    <!--   Ewentualnie dla aplikacji reaktywnych-->
    <!--    <dependency>-->
    <!--        <groupId>org.springframework.cloud</groupId>-->
    <!--        <artifactId>spring-cloud-starter-gateway</artifactId>-->
    <!--    </dependency>-->
    
   <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
   
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
</dependencies>
```

#### Config w `config-server`

```yaml
spring:
  application:
    name: gateway
  cloud:
    config:
      discovery:
        enabled: true
        service-id: config-server
    gateway:
      mvc: # Dla aplikacji mvc jeśli dla reaktywnych to usunąć.
        routes: # Dodajemy tutaj routy do serwisów
          - id: example-service # Umieścić dokładną nazwę serwisu z pliku konfiguracyjnego danego serwiu.
            uri: http://localhost:XXXX # Zamień XXXX na port serwisu
            predicates:
              - Path=/api/v1/example/** # Ścieżka do serwisu wywołanie serwisu "http://localhost:8222/api/v1/example/exampleEndpoint"
            # To samo musi być w kontrolerze serwisu który chcemy wywołać tzn. @RequestMapping("/v1/currencyAccounts") lub jeśli nie 
            # narzuciliśmy w configu ścieżki "api" to @RequestMapping("/api/v1/currencyAccounts")

eureka:
  client:
    register-with-eureka: false # Czy chcemy aby eureka nam rejestrowała serwis.

server:
  port: 8222

management:
  tracing:
    sampling:
      probability: 1.0
```

#### Config w `Gateway`

```yaml
spring:
  application:
    name: gateway

  config:
    import: optional:configserver:http://localhost:8888

```

#### Security w `Gateway`

Aby dodać możliwość autentykacji tokenem JWT trzeba w `pom.xml` dodać zależność:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
   
    <dependency>
        <groupId>com.auth0</groupId>
        <artifactId>java-jwt</artifactId>
        <version>4.3.0</version>
    </dependency>
</dependencies>
```
`SecurityConfig` - Jest odpowiedzialne za konfigurację zabezpieczeń w aplikacji.

Następnie stworzyć klasę `SecurityConfig` i dodać konfigurację:



```java
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserAuthProvider userAuthProvider;

    @Autowired
    private JwtRedirectionFilter jwtRedirectionFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { // Konfiguracja zabezpieczeń

        httpSecurity.csrf(AbstractHttpConfigurer::disable) // Ochrona CSRF wyłączona
                .addFilterBefore(new JwtAuthFilter(userAuthProvider), BasicAuthenticationFilter.class) // Dodanie filtra autoryzacji
                .addFilterBefore(jwtRedirectionFilter, BasicAuthenticationFilter.class)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Wyłączenie zarządzania sesją
                .authorizeHttpRequests(request -> // Konfiguracja zabezpieczeń
                        request.requestMatchers(HttpMethod.POST,  "api/v1/auth/login","api/v1/auth/register", "/error", "api/v1/currencies/search","api/v1/currencyAccounts/error").permitAll() // Pozwala na wykonywanie zapytań POST na adresach: /login, /register
                                .requestMatchers(HttpMethod.GET).permitAll() // Pozwala na wykonywanie zapytań GET na adresach: /search/**
                                .anyRequest().authenticated()); // Wymaga autoryzacji dla pozostałych zapytań
        return httpSecurity.build();
    }
}
```
`JwtAuthFilter` - Jest odpowiedzialne za autoryzację tokenem JWT i jest.

Następnie stworzyć klasę `JwtAuthFilter` i dodać konfigurację:

```java
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthProvider userAuthProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Jeśli token nie jest dostępny w nagłówku, spróbuj odczytać go z URL
        if (token == null || !token.startsWith("Bearer ")) {
            token = request.getParameter("JWTtoken");
        } else {
            token = token.substring(7); // Usuń prefiks "Bearer " z tokena
        }

        if (token != null) {
            try {
                SecurityContextHolder.getContext().setAuthentication(userAuthProvider.validateToken(token));
            } catch (RuntimeException e) {
                SecurityContextHolder.clearContext();
                throw e;
            }
        }

        filterChain.doFilter(request, response);
    }
}
```
`JwtRedirectionFilter` - Jest odpowiedzialne za przekierowywanie tokena JWT z serwisu do bramy tzn. że na danemy wywołanemu serwisowi zostanie dostarczony token JWT.

Następnie stworzyć klasę `JwtRedirectionFilter` i dodać konfigurację:

```java
@Component
public class JwtRedirectionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() != null) {
            String token = authentication.getCredentials().toString();
            ((HttpServletResponse) response).addHeader("Authorization", "Bearer " + token);
        }
        chain.doFilter(request, response);
    }
}
```
`UserAuthProvider` - Jest odpowiedzialne za tworzenie i walidację tokena JWT.

Następnie stworzyć klasę `UserAuthProvider` i dodać konfigurację:

```java
@RequiredArgsConstructor
@Component
public class UserAuthProvider {
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());

    }

    public String createToken(UserDto dto) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000);
        return JWT.create()
                .withIssuer(dto.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("id", dto.getId())
                .withClaim("firstName", dto.getFirstName())
                .withClaim("lastName", dto.getLastName())
                .withClaim("email", dto.getEmail())
                .sign(Algorithm.HMAC256(secretKey));

    }

    public Authentication validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT decodedJWT = verifier.verify(token);

            UserDto user = UserDto.builder()
                    .username(decodedJWT.getIssuer())
                    .id(decodedJWT.getClaim("id").asLong())
                    .firstName(decodedJWT.getClaim("firstName").asString())
                    .lastName(decodedJWT.getClaim("lastName").asString())
                    .token(decodedJWT.getToken())
                    .email(decodedJWT.getClaim("email").asString())
                    .build();
            return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

        } catch (JWTVerificationException exception) {
            if(exception.getMessage().contains("The Token has expired on")) {
                DecodedJWT decodedJWT = JWT.decode(token);
                UserDto user = UserDto.builder()
                        .username(decodedJWT.getIssuer())
                        .id(decodedJWT.getClaim("id").asLong())
                        .firstName(decodedJWT.getClaim("firstName").asString())
                        .lastName(decodedJWT.getClaim("lastName").asString())
                        .token(decodedJWT.getClaim("token").asString())
                        .email(decodedJWT.getClaim("email").asString())
                        .build();
                String newToken = createToken(user);
                return new UsernamePasswordAuthenticationToken(user, newToken, Collections.emptyList());
            }
            else {
                throw new AppExeption("Does not apply to token", HttpStatus.UNAUTHORIZED);
            }

        }
    }
}
```
## Tworzenie nowego serwisu

Aby stworzyć nowy mikroserwis trzeba dodać do niego
zależności `config client`, `eureka discovery client`, `spring boot actuator` oraz `spring boot starter web`.

`config client` - pozwala na pobieranie konfiguracji z serwera konfiguracyjnego.

`eureka discovery client` - pozwala na rejestrację serwisu w serwerze Eureka.

`spring boot actuator` - pozwala na monitorowanie aplikacji.

### Dependency

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
</dependencies>
```

Następnie trzeba dodać konfigurację do pliku `application.yml`:

```yaml
spring:
  application:
    name: NAZWA_TWOJEGO_SERWISU
  config:
    import: optional:configserver:http://localhost:XXXX # Optional znaczy to że jeśli nie znajdzie serwera konfiguracyjnego 
      # to nie zwróci błędu i będzie działać z domyślnymi wartościami(czyli z tego pliku).
    # Zamień XXXX na port serwera konfiguracyjnego.
```

Nastpnie dodaj plik `NAZWA_TWOJEGO_SERWISU.yml` do serwera konfiguracyjnego w `resources/configurations`. W tym pliku
dodaj konfigurację dla swojego serwisu.

```yaml
spring:
  application:
    name: NAZWA_TWOJEGO_SERWISU

server:
  port: XXXX # Zamień XXXX na port na którym ma działać serwis
  servlet:
    context-path: /api

# To jest ustawienie dla eureki
management:
  tracing:
    sampling:
      probability: 1.0
```

## Komunikacja między mikrousługami

Jeśli chcesz aby mikrousługi komunikowały się ze sobą, musisz dodać odpowiednie zależności i konfiguracje do każdej z
nich. Domyślnie przyjmuje że jest zaimplementowany token JWT. Jeśli nie nie iplementuj `FeignConfig` i `getToken`.

Komunikacja wygląda w ten sposób:
`gateway` -> `mikrousługa_1` -> `gateway` -> `mikrousługa2` ->`gateway` -> `mikrousługa_1` -> `gateway` -> `wynik`

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

Dodaj adnotację `@FeignClient` do interfejsu, który będzie używany do komunikacji z inną mikrousługą. Zmienna `name`
musi być taka sama jak nazwa aplikacji w pliku konfiguracyjnym `application.yml` w mikrousłudze z którą chcesz się
połączyć. Zmienna `url` to adres mikrousługi do której chcesz się połączyć. W przykładzie poniżej jest
to `http://localhost:8073/api`

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