# Kantor Backend - spring boot/Java

## Wstęp

Kiedyś dodać

## Wymagania

- Java 17
- Spring Boot 3.2.3
- Maven (wersja zależna od środowiska deweloperskiego)
- IntelliJ IDEA 2024.1 (lub inny preferowany IDE)
- Azure Spring Cloud 5.11.0
- PayPal SDK 1.7.2
- Spring Boot Starter WebFlux 3.2.3
- JSON 20210307
- PayPal REST API SDK 1.14.0
- Spring Security Config
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Thymeleaf
- Bootstrap 5.1.3
- PostgreSQL Driver 42.7.1
- Lombok 1.18.30
- Spring Boot Starter Security
- MapStruct 1.5.5.Final
- Java JWT 4.3.0
- Spring Kafka
- Spring Boot Starter Test
- JUnit 4.13.2
- Spring Boot Starter Actuator

## Jak zbudować i uruchomić projekt

1. Sklonuj repozytorium na swoje lokalne środowisko deweloperskie.
2. Otwórz projekt w IntelliJ IDEA.
3. Uruchom `mvn clean install` w terminalu, aby zbudować projekt.
4. Uruchom klasę `BackendApplication` jako aplikację Spring Boot.

## Testy

Aby uruchomić testy, wejdź w `Maven -> backend -> Lifecycle -> test`. Jest też możliwośc aby uruchomić testy jednostkowe przy każdym uruchomieniu.  
Wejdź w `Run/Debug Configurations -> Modify options -> Before launch -> Add -> Run Maven Goal -> Command: test`.

## Struktura projektu

Projekt składa się z następujących głównych części:

- `BackendApplication.java`: Główna klasa aplikacji Spring Boot.
- `MVC/model/`: Klasy reprezentujące model bazy danych.
- `MVC/dto`: Klasy DTO (Data Transfer Object).
- `MVC/config`: Klasy konfiguracyjne.
- `MVC/controller`: Endpointy.
- `MVC/exeption`: Wyjątki.
- `MVC/mapper`: Interfejsy mapujące. `Encje -> DTO, DTO -> Encje`
- `MVC/repository`: Interfejsy repozytorium służą jako warstwa abstrakcji między warstwą usług a danymi.
- `MVC/service`: Klasy z logiką biznesową.
# API Documentation

## Overview

This document provides information on the API endpoints for the Kantor Backend project.

[//]: # (TODO: Zrobić API Documentation)
# API Documentation

## Overview

This document provides information on the API endpoints for the Kantor Backend project.

## Endpoints

### GET /api/currencies

Returns a list of all currencies.

#### Parameters

None

#### Response

Array of `CurrencyDto` objects:

```json
[
{
"id": 1,
"code": "USD",
"name": "United States Dollar"
},
{
"id": 2,
"code": "EUR",
"name": "Euro"
}
]
```

### POST /api/currencies

Creates a new currency.

#### Parameters

`CurrencyDto` object:

```json
{
"code": "GBP",
"name": "British Pound"
}
```

#### Response

Created `CurrencyDto` object:

```json
{
"id": 3,
"code": "GBP",
"name": "British Pound"
}
```

### PUT /api/currencies/{id}

Updates an existing currency.

#### Parameters

`CurrencyDto` object:

```json
{
"code": "GBP",
"name": "British Pound Sterling"
}
```

#### Response

Updated `CurrencyDto` object:

```json
{
"id": 3,
"code": "GBP",
"name": "British Pound Sterling"
}
```

### DELETE /api/currencies/{id}

Deletes an existing currency.

#### Parameters

None

#### Response

None

### GET /api/accounts

Returns a list of all accounts.

#### Parameters

None

#### Response

Array of `AccountDto` objects:

```json
[
{
"id": 1,
"name": "Account 1",
"balance": 1000
},
{
"id": 2,
"name": "Account 2",
"balance": 2000
}
]
```

### POST /api/accounts

Creates a new account.

#### Parameters

`AccountDto` object:

```json
{
"name": "Account 3",
"balance": 3000
}
```

#### Response

Created `AccountDto` object:

```json
{
"id": 3,
"name": "Account 3",
"balance": 3000
}
```

### PUT /api/accounts/{id}

Updates an existing account.

#### Parameters

`AccountDto` object:

```json
{
"name": "Account 3 Updated",
"balance": 3500
}
```

#### Response

Updated `AccountDto` object:

```json
{
"id": 3,
"name": "Account 3 Updated",
"balance": 3500
}
```

### DELETE /api/accounts/{id}

Deletes an existing account.

#### Parameters

None

#### Response

None

## Error Handling

In case of an error, the API will return an HTTP status code and a JSON object with the following structure:

```json
{
"timestamp": "2022-03-01T12:00:00.000+00:00",
"status": 400,
"error": "Bad Request",
"message": "Invalid request",
"path": "/api/currencies"
}
```
## Licencja

Informacje o licencji, jeśli taka istnieje.