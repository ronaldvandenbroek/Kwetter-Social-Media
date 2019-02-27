package nl.fontys.kwetter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidModelException extends Exception {

    public InvalidModelException() {
    }

    public InvalidModelException(String message) {
        super(message);
    }
}
