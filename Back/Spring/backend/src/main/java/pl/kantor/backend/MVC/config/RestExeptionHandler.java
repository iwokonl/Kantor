package pl.kantor.backend.MVC.config;


        import org.springframework.http.ResponseEntity;

        import org.springframework.web.bind.annotation.ControllerAdvice;
        import org.springframework.web.bind.annotation.ExceptionHandler;
        import org.springframework.web.bind.annotation.ResponseBody;
        import pl.kantor.backend.MVC.dto.ErrorDto;
        import pl.kantor.backend.MVC.exeption.AppExeption;

@ControllerAdvice
public class RestExeptionHandler {
    @ExceptionHandler(AppExeption.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleAppExeption(AppExeption e) { // metoda obsługująca wyjątki
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorDto(e.getMessage())); // zwracamy status błędu i wiadomość
    }
}
