package nl.fontys.kwetter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CannotLoginException extends Exception {

    public CannotLoginException() {
    }

    public CannotLoginException(String message) {
        super(message);
    }
}
