package nl.fontys.kwetter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserDoesNotExist extends Exception {

    public UserDoesNotExist() {
        super();
    }

    public UserDoesNotExist(String message) {
        super(message);
    }
}
