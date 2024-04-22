Jeśli tworzysz nowy serwis to dodaj: config client, eureka discovery client, spring boot actuator

https://www.youtube.com/watch?v=KJ0cSvYj41c&t=3407s
Róbcie z tego bo amen XD

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