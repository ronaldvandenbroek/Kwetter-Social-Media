package nl.fontys.kwetter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NotImplementedException extends Exception {

    public NotImplementedException(String message) {
        super(message);
    }
}