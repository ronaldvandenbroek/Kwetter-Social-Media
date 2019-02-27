package nl.fontys.kwetter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class UsernameAlreadyExists extends Exception {
    public UsernameAlreadyExists() {
    }

    public UsernameAlreadyExists(String message) {
        super(message);
    }
}
