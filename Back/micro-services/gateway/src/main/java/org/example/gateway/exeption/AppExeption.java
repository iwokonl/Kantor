package org.example.gateway.exeption;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class AppExeption extends RuntimeException { // Klasa wyjątku aplikacji
    private final HttpStatus httpStatus;
    private String theNameOfThMicroservice;

    public AppExeption(String message, String theNameOfThMicroservice, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.theNameOfThMicroservice = theNameOfThMicroservice;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}