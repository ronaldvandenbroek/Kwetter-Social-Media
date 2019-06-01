package nl.fontys.kwetter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CouldNotDeleteModelException extends RuntimeException {

    public CouldNotDeleteModelException(String message) {
        super(message);
    }
}
