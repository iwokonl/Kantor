package org.example.currencies.config;


import org.example.currencies.dto.ErrorDto;
import org.example.currencies.exeption.AppExeption;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class RestExeptionHandler {
    @ExceptionHandler(AppExeption.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleAppExeption(AppExeption e) { // metoda obsługująca wyjątki
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorDto(e.getMessage())); // zwracamy status błędu i wiadomość
    }
}