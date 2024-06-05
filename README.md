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
- [Java 22.0.1](https://jdk.java.net/22/)
- [JDK 22](https://www.oracle.com/java/technologies/downloads/#java22)
- [Spring boot 3.2.x](https://start.spring.io)
- [TypeScript 5.3.x](https://www.typescriptlang.org/download)
- [Node.js 21.7.x](https://nodejs.org/en/download/prebuilt-installer)
- [Angular 17.3.x](https://www.npmjs.com/package/@angular/cli?activeTab=versions)
- [PostgreSQL 16.2](https://www.postgresql.org/download/)
- [Apache Kafka 3.6.x](https://kafka.apache.org/downloads)


### Instalacja

Postępuj zgodnie z poniższymi krokami w celu zainstalowania środowiska oraz aplikacji. Na końcu zapoznaj się z Uruchomieniem aplikacji.

```bash
# Sklonuj repozytorium:
mkdir Projekt
git clone https://github.com/iwokonl/Kantor.git
cd Projekt
```
### Uruchamianie aplikacji

- Uruchomienie Docker
```bash
docker-compose up -d
```

- Frontend (Angular)
```bash
cd Front/kantor-angular
npm install -g angular-cli@17.3.0
npm run
ng b
cd /Projekt
```
- Backend (Spring Boot)
Przejdź do katalogu backend i uruchom polecenie Maven, aby oczyścić projekt, zbudować plik .jar oraz zainstalować zależności:
```bash
cd Kantor/Back/mikro-services
mvn clean install
cd target
java -jar [nazwa_pliku_jar] # np. java -jar kantor-0.0.1-SNAPSHOT.jar w Target. Kolejność uruchamiania mikroserwisów jest określona w README.MD w ms.
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
