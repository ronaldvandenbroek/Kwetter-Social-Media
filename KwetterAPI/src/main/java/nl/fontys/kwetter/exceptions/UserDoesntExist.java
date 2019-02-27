package nl.fontys.kwetter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserDoesntExist extends Exception {

    public UserDoesntExist() {
    }

    public UserDoesntExist(String message) {
        super(message);
    }
}
