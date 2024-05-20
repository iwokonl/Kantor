package org.example.paypal.config;


import org.example.paypal.dto.ErrorDto;
import org.example.paypal.exeption.AppExeption;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class RestExeptionHandler {
    @ExceptionHandler(AppExeption.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleAppExeption(AppExeption e) { // metoda obsługująca wyjątki
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorDto(e.getMessage(),e.getTheNameOfThMicroservice())); // zwracamy status błędu i wiadomość
    }
}
