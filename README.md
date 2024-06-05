# Kantor


![GitHub last commit](https://img.shields.io/github/last-commit/iwokonl/Kantor)   ![GitHub Release Date](https://img.shields.io/github/release-date/Iwokonl/Kantor)


## Opis
Ta aplikacja Spring Boot zapewnia solidne rozwiązanie do pobierania i przechowywania kursów wymiany walut z interfejsu API Narodowego Banku Polskiego (NBP). Zaprojektowana z myślą o nowoczesnych praktykach tworzenia oprogramowania, wykorzystuje architekturę Model-View-Controller (MVC), aby zapewnić czystą separację zagadnień i zwiększyć łatwość konserwacji.



## Spis treści
- [README Mikroserwisów](https://github.com/iwokonl/Kantor/tree/main/Back/micro-services)

- [README Angulara](https://github.com/iwokonl/Kantor)



- [Jak zacząć?](#Jak-zacząć?) 
- [Wymagania](#prerequisites)
- [Instalacja](#Instalacja)
- [Uruchamianie aplikacji](#running-the-application) 
- [Przykłady użycia](#Przykłady-użycia) 
- [API](#api)
- [Autorzy](#authors) 
- [Licencje](#license) 
- [Podziękowania](#podzienkowania) 

### Jak zacząć?
Przejdź po wymaganiach, żeby upewnić się, że posiadasz wszystkie niezbędę oprogramowanie póżniej przejdź do instalacji.
### Wymagania wstępne


Przed instalacją upewnij się, że na komputerze zainstalowane jest następujące oprogramowanie:

- [Node.js 21.7.2]([https://nodejs.org/en/download/package-manager](https://nodejs.org/en/download/prebuilt-installer))
- [JDK 22](https://www.oracle.com/java/technologies/downloads/#java22)
- [Apache Kafka 3.6.1](https://kafka.apache.org/downloads)
- [PostgreSQL 16.2](https://www.postgresql.org/download/)
- [Java 22.0.1](https://jdk.java.net/22/)
- Angular 17.3.0
- Spring boot 3.2.5

### Instalacja

Postępuj zgodnie z poniższymi krokami w celu zainstalowania środowiska oraz aplikacji. Na końcu zapoznaj się z Uruchomieniem aplikacji.

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

- Backend (Spring Boot)
Przejdź do katalogu backend i uruchom polecenie Maven, aby oczyścić projekt, zbudować plik .jar oraz zainstalować zależności:
```bash
cd backend
./mvnw clean install
```
- Uruchomienie aplikacji
 Po zakończeniu instalacji, przejdź do katalogu target i użyj polecenia Java, aby uruchomić aplikację:
```bash
cd target
java -jar nazwa_pliku.jar
```


- Uruchom Angular
 Po uruchomieniu backendu, przejdź do katalogu frontend i uruchom aplikację Angular za pomocą następujących poleceń:
```bash
cd frontend
ng serve
# Aplikacja będzie dostępna pod adresem http://localhost:4200
```
### Przykłady użycia

### API
Aplikacja wykorzystuje interfejs [API Narodowego Banku Polskiego (NBP)](https://api.nbp.pl) do pobierania kursów walut oraz cen złota w formacie XML oraz JSON.
Kantor używa, także [API Paypal](https://developer.paypal.com/api/rest/) do obsługi płatności.
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
