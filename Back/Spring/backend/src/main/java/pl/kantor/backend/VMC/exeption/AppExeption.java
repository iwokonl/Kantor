package pl.kantor.backend.VMC.exeption;

import org.springframework.http.HttpStatus;

public class AppExeption extends RuntimeException { // Klasa wyjątku aplikacji
    private final HttpStatus httpStatus;
    public AppExeption(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
