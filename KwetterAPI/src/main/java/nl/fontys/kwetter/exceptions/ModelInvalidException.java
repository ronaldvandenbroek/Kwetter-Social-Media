package nl.fontys.kwetter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ModelInvalidException extends Exception {

    public ModelInvalidException() {
    }

    public ModelInvalidException(String message) {
        super(message);
    }
}
