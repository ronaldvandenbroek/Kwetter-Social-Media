package nl.fontys.kwetter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CouldNotDelete extends Exception {

    public CouldNotDelete() {
    }

    public CouldNotDelete(String message) {
        super(message);
    }
}
