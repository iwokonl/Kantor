package pl.zeto.backend.VMC.config;


import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.zeto.backend.VMC.dto.ErrorDto;
import pl.zeto.backend.VMC.exeption.AppExeption;

@ControllerAdvice
public class RestExeptionHandler {
    @ExceptionHandler(AppExeption.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleAppExeption(AppExeption e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorDto(e.getMessage()));
    }
}
