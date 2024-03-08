# Wybierz obraz bazowy z odpowiednią wersją Javy; użyjemy Java 17
FROM openjdk:21-jdk-alpine

# Ustaw zmienną środowiskową do przechowywania informacji o portach, na których nasłuchuje aplikacja
ARG JAR_FILE=target/*.jar
COPY ./target/backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]