package pl.zeto.backend.VMC.exeption;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    // Możesz również dodać konstruktory obsługujące przyczyny (cause) lub supresję i zapisywanie stosu wywołań
}