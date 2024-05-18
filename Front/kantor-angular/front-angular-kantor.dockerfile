# Wybierz obraz z Node.js
FROM node:21.7.2

# Ustaw katalog roboczy w kontenerze
WORKDIR /app

# Skopiuj plik package.json i package-lock.json
COPY package*.json ./

# Zainstaluj zależności
RUN npm install

# Skopiuj resztę kodu źródłowego
COPY . .

# Zbuduj aplikację
RUN npm run build

# Użyj obrazu nginx do serwowania zbudowanej aplikacji
FROM nginx:1.25.4

# Skopiuj zbudowaną aplikację do katalogu serwera nginx
COPY --from=0 /app/dist/kantor-angular /usr/share/nginx/html

# Ustaw port na którym nginx będzie nasłuchiwał
EXPOSE 80

# Uruchom nginx
CMD ["nginx", "-g", "daemon off;"]
