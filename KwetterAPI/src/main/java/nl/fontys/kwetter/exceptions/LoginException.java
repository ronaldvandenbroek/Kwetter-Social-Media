package nl.fontys.kwetter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class LoginException extends Exception {

    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }
}
