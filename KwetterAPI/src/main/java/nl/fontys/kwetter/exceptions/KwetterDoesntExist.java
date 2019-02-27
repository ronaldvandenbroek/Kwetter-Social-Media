package nl.fontys.kwetter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class KwetterDoesntExist extends Exception {

    public KwetterDoesntExist() {
    }

    public KwetterDoesntExist(String message) {
        super(message);
    }
}
