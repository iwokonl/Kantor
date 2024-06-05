# Kantor


![GitHub last commit](https://img.shields.io/github/last-commit/iwokonl/Kantor)

## Opis
Ta aplikacja Spring Boot zapewnia solidne rozwiązanie do pobierania i przechowywania kursów wymiany walut z interfejsu API Narodowego Banku Polskiego (NBP). Zaprojektowana z myślą o nowoczesnych praktykach tworzenia oprogramowania, wykorzystuje architekturę Model-View-Controller (MVC), aby zapewnić czystą separację zagadnień i zwiększyć łatwość konserwacji.



## Spis treści
- [README Mikroserwisów](https://github.com/iwokonl/Kantor/tree/main/Back/micro-services)

- [README Angulara](https://github.com/iwokonl/Kantor)



- [Jak zacząć?](#getting-started) 
- [Wymagania](#prerequisites)
- [Instalacja](#Instalacja)
- [Uruchamianie aplikacji](#running-the-application) 
- [Usage](#usage) 
- [API](#api)
- [Autorzy](#authors) 
- [Licencje](#license) 
- [Podziękowania](#podzienkowania) 



### Wymagania wstępne


Przed instalacją upewnij się, że na komputerze zainstalowane jest następujące oprogramowanie:

- [Node.js 20.10.0](https://nodejs.org/en/download/package-manager)
- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Apache Kafka 3.6.1](https://kafka.apache.org/downloads)
- [PostgreSQL 16.2](https://www.postgresql.org/download/)
- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- Angular 17.2.1
- Spring boot 3.2.3

### Instalacja

Postępuj zgodnie z poniższymi krokami w celu zainstalowania środowiska oraz aplikacji.

```bash
# Sklonuj repozytorium:
git clone https://github.com/iwokonl/Kantor.git
cd yourproject

# Instalacja zależności dla Angular
cd frontend
npm install
# Uruchomienie aplikacji Angular:
ng serve

# Uruchomienie aplikacji Spring Boot:
cd ../backend
./mvnw install

# Konfiguracja i uruchomienie Apache Kafka:
# Skonfiguruj server.properties zgodnie z dokumentacją.
# Uruchom Kafka:
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```
### Uruchamianie aplikacji
- Uruchom Angular
```bash
cd frontend
ng serve
# Aplikacja będzie dostępna pod adresem http://localhost:4200
```
- Uruchamianie backendu (Spring Boot)
Utwórz bazę danych dla aplikacji.
Skonfiguruj plik application.properties w Spring Boot, aby wskazywał na bazę danych PostgreSQL. Przykład konfiguracji:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/nazwa_bazy_danych
spring.datasource.username=nazwa_uzytkownika
spring.datasource.password=haslo
spring.jpa.hibernate.ddl-auto=update
```
- Uruchom aplikację Spring Boot:
```bash
cd backend
./mvnw spring-boot:run
```
### API
Aplikacja wykorzystuje interfejs [API Narodowego Banku Polskiego (NBP)](https://api.nbp.pl) do pobierania kursów walut oraz cen złota w formacie XML oraz JSON.
### Autorzy
[Iwo Stanisławski](https://github.com/iwokonl/), [Bogumił Różański](https://github.com/brozanski), [Paweł Ściślewski](https://github.com/Zaikouu), Bartosz Zalewski, [Patryk Seligowski](https://github.com/Patryk920n)
### Licencje
Ten projekt został stworzony jako część kursu studiów na Uniwersytecie Warmińsko-Mazurskim w Olsztynie. Projekt nie jest przeznaczony do użytku komercyjnego.

Ograniczenia
- Projekt jest przeznaczony wyłącznie do celów edukacyjnych.
- Bez zgody autora nie można używać kodu źródłowego w celach komercyjnych.
- Wszelkie kopie lub rozpowszechnianie kodu źródłowego muszą zawierać tę    informację o prawach autorskich i ograniczeniach

### Podziękowania
Prziękowania należą się firmie ZETO Software za czuwanie nad projektem oraz cenne rady.
Również wyrażam ogromną wdzięczność Panu Bartoszowi Nowakowi, prowadzącemu przedmiot, za jego nieocenioną pomoc, cierpliwość i profesjonalne podejście.
