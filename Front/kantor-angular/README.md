# Kantor Angular

## Spis treści

- [README Mikroserwisów](https://github.com/iwokonl/Kantor/tree/main/Back/micro-services)

- [README Główne](https://github.com/iwokonl/Kantor/tree/main/Front/kantor-angular)


1. [Opis projektu](#opis-projektu)
2. [Technologie](#technologie)
3. [Instalacja](#instalacja)
4. [Uruchomienie](#uruchomienie)
5. [Testy](#testy)
6. [Komponenty](#komponenty)
7. [Serwisy](#serwisy)
8. [Stylowanie](#stylowanie)
9. [Zależności](#zależności)
10. [Rozwój](#rozwój)

## Opis projektu
Projekt kantoru wymiany walut online. Aplikacja pozwala użytkownikom na przeglądanie aktualnych kursów walut, wykresów historycznych kursów, zakładanie kont walutowych, przeprowadzanie transakcji walutowych i zarządzanie swoim kontem. Użytkownicy mogą również zarejestrować się, zalogować się i wylogować z aplikacji.
Doładowanie konta oraz wypłata środków jest możliwa po zalogowaniu się do aplikacji.
W obecnej wersji, kursy walut są pobierane z API NBP, a użytkownicy mogą przeglądać kursy walut z różnych krajów.
Transakcja realizowana jest poprzez PayPal (w tym projekcie jest to wyłącznie sandbox).
Wszystkie operacje są zapisywane w bazie danych, a użytkownik może sprawdzić historię swoich transakcji.
Aplikacja jest napisana w Angularze, wykorzystuje TypeScript, SCSS, Bootstrap, Axios, Chart.js i inne technologie.
Jest to projekt edukacyjny, który ma na celu nauczenie się tworzenia aplikacji internetowych za pomocą Angulara i innych technologii webowych.


## Technologie

1. **Angular**: Jest to platforma do budowy aplikacji internetowych. Umożliwia tworzenie skomplikowanych interfejsów użytkownika za pomocą komponentów i szablonów. Angular jest napisany w TypeScript, co ułatwia zarządzanie kodem i debugowanie.

2. **TypeScript**: Jest to język programowania oparty na JavaScript, który dodaje statyczne typowanie. Ułatwia to zarządzanie kodem i debugowanie, szczególnie w dużych projektach.

3. **SCSS**: Jest to preprocesor CSS, który dodaje wiele użytecznych funkcji do języka, takich jak zmienne, mixiny, dziedziczenie i inne. Ułatwia to pisanie czystego, łatwego do zarządzania kodu CSS.

4. **Axios**: Jest to popularna biblioteka JavaScript do wykonywania żądań HTTP. Umożliwia łatwe wykonywanie żądań GET, POST i innych, a także obsługuje Promise, co ułatwia zarządzanie asynchronicznymi operacjami.

5. **NPM (Node Package Manager)**: Jest to menedżer pakietów dla języka programowania JavaScript. Umożliwia łatwe zarządzanie zależnościami projektu.

6. **Bootstrap**: Jest to biblioteka CSS, która zawiera wiele predefiniowanych stylów i komponentów, które ułatwiają tworzenie atrakcyjnych interfejsów użytkownika.

7. **Chart.js**: Jest to biblioteka JavaScript do tworzenia interaktywnych wykresów na stronach internetowych.

9. **RxJS**: Jest to biblioteka do programowania reaktywnego, która ułatwia zarządzanie asynchronicznymi operacjami i strumieniami danych.

10. **Angular Material**: Jest to biblioteka komponentów Angulara, która zawiera wiele predefiniowanych komponentów, które ułatwiają tworzenie atrakcyjnych interfejsów użytkownika.

11. **Snackbar**: Jest to komponent Angulara, który wyświetla krótkie powiadomienia na dole ekranu. Jest to przydatne do wyświetlania informacji zwrotnych dla użytkownika.


## Instalacja
Instalacja NPM i Angular CLI:

1. **Node.js i NPM**: Angular wymaga Node.js i NPM (Node Package Manager) do zarządzania zależnościami. Możesz zainstalować Node.js i NPM odwiedzając stronę [Node.js](https://nodejs.org/en/download/) i pobierając odpowiedni instalator dla swojego systemu operacyjnego. Po zainstalowaniu Node.js, NPM zostanie zainstalowany automatycznie.

2. **Angular CLI**: Angular CLI (Command Line Interface) to narzędzie, które ułatwia tworzenie i zarządzanie projektami Angular. Możesz zainstalować Angular CLI za pomocą NPM, uruchamiając następujące polecenie w terminalu:

```bash
npm install -g @angular/cli
```



## Uruchomienie
Uruchomienie projektu Angular:

Po zainstalowaniu Angular CLI i utworzeniu projektu Angular, możesz uruchomić swój projekt za pomocą polecenia `ng serve`. To polecenie uruchomi serwer deweloperski, który automatycznie odświeży twoją aplikację po każdej zmianie w plikach źródłowych.

```bash
ng serve
```

Domyślnie, aplikacja będzie dostępna pod adresem `http://localhost:4200/` w przeglądarce.

Jeśli chcesz uruchomić projekt, który jest już skonfigurowany (tak jak ten, nad którym pracujesz), możesz użyć polecenia `npm start`, które jest zdefiniowane w pliku `package.json`. To polecenie uruchomi skrypt `ng serve`.

```bash
npm start
```

Podobnie jak wcześniej, aplikacja będzie dostępna pod adresem `http://localhost:4200/` w przeglądarce.


## Testy
Dotychczas testy na frontendzie były wykonywane manualnie.
W przyszłości planujemy dodać testy jednostkowe i testy end-to-end do projektu.


## Komponenty
Komponenty z `app.module.ts`:

1. `AppComponent`: Jest to główny komponent aplikacji, który jest punktem wejścia dla innych komponentów.

2. `AuthContentComponent`: Komponent odpowiedzialny za wyświetlanie treści związanej z autoryzacją.

3. `WelcomeContentComponent`: Komponent wyświetlający powitalną treść użytkownika.

4. `LoginFormComponent`: Komponent odpowiedzialny za formularz logowania.

5. `ContentsComponent`: Komponent wyświetlający główną treść aplikacji.

6. `RegisterFormComponent`: Komponent odpowiedzialny za formularz rejestracji.

7. `ButtonsComponent`: Komponent wyświetlający przyciski.

8. `HomeComponentComponent`: Komponent wyświetlający stronę główną.

9. `HeaderComponent`: Komponent wyświetlający nagłówek aplikacji.

10. `SearchBarComponent`: Komponent odpowiedzialny za pasek wyszukiwania.

11. `SearchResultsComponent`: Komponent wyświetlający wyniki wyszukiwania.

12. `CurrencyAccountComponent`: Komponent odpowiedzialny za wyświetlanie konta walutowego.

13. `CurrencyDetailComponent`: Komponent wyświetlający szczegóły waluty.

14. `CurrencyChartComponent`: Komponent wyświetlający wykres waluty.

15. `SettingsComponent`: Komponent odpowiedzialny za wyświetlanie ustawień.



## Serwisy
Plik `axios.service.ts` zawiera deklarację serwisu `AxiosService`, który jest serwisem Angulara. Serwis ten jest odpowiedzialny za zarządzanie autoryzacją użytkownika i wykonywanie żądań HTTP za pomocą biblioteki axios. Oto szczegółowy opis metod zadeklarowanych w tym serwisie:

1. `getAuthTocken()`: Ta metoda zwraca token autoryzacji zapisany w pamięci lokalnej przeglądarki. Jeśli token nie istnieje, zwraca `null`.

2. `setAuthTocken(token: string | null)`: Ta metoda zapisuje podany token autoryzacji w pamięci lokalnej przeglądarki. Jeśli podany token jest `null`, usuwa token z pamięci lokalnej. Po ustawieniu tokenu, metoda emituje nowy status autoryzacji za pomocą `authStatusSubject`.

3. `logout()`: Ta metoda usuwa token autoryzacji z pamięci lokalnej, emituje status autoryzacji jako `false` i przekierowuje użytkownika na stronę główną.

4. `checkAuthTocken()`: Ta metoda sprawdza, czy token autoryzacji jest prawidłowy. Jeśli token jest `null`, `undefined`, pusty lub równy 'null', usuwa token z pamięci lokalnej.

5. `request(method: string, url: string, data: any)`: Ta metoda wykonuje żądanie HTTP za pomocą metody axios. Przed wykonaniem żądania, sprawdza token autoryzacji i jeśli istnieje, dodaje go do nagłówków żądania.

6. `requestWithOutData(method: string, url: string)`: Ta metoda jest podobna do metody `request`, ale nie przekazuje żadnych danych w żądaniu.

Wszystkie metody są publiczne, co oznacza, że mogą być wywoływane z zewnątrz klasy. Serwis `AxiosService` jest dostarczany w korzeniu, co oznacza, że jest dostępny dla całej aplikacji.

Inne serwisy

1. `app-routing.module.ts`: Jest to moduł, który definiuje trasy dla aplikacji. Każda trasa składa się z ścieżki i komponentu, który ma być wyświetlony, gdy użytkownik nawiguje do tej ścieżki.

2. `currency.service.ts`: Jest to serwis, który dostarcza metody do pobierania szczegółów waluty i historii waluty z API. Używa `HttpClient` do wykonywania żądań HTTP.

3. `currency-flags.service.ts`: Jest to serwis, który dostarcza flagi dla różnych walut. Używa biblioteki `country-flag-emoji-polyfill` do uzyskania emoji flagi dla danego kodu waluty.

4. `user.service.ts`: Jest to serwis, który dostarcza metody do pobierania informacji o użytkowniku z backendu. Używa `HttpClient` do wykonywania żądań HTTP.


## Stylowanie
Postanowiliśmy zaprojektować całą stronę w ciemnej kolorystyce, ze wspólnym motywem dla wszystkich stron i kompnentów.
Próbujemy unikać gotowych rozwiązań pokroju Bootstrap i sami projektujemy od zera nasz design. Projekt designu i layoutu wykonany został w aplikacji Figma.
Stawiamy na nowoczesny wygląd z użyciem przezroczytych, rozmazanych ramek dających szklany wygląd.


## Zależności

[country-flag-emoji-polyfill](https://github.com/talkjs/country-flag-emoji-polyfill)
to oduł pozwalający na wyświetlanie emoji flagi dla danego kodu kraju. Używamy go w aplikacji do wyświetlania flagi dla danej waluty.
Jest użyty w projekcie z powodu braku wsparcia dla emoji flag w niektórych przeglądarkach na Windowsie.


## Rozwój
- Dodanie możliwości zmiany motywu kolorystycznego
- Dodanie możliwości zmiany języka
- Dodanie możliwości zmiany domyślnej waluty
- Rozszerzenie funkcjonalności konta użytkownika
- Dodanie obsługi innych metod płatności
- Dodanie obsługi innych API kursów walut
- Dodanie obsługi kryptowalut
