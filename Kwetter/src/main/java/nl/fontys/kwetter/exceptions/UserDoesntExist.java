package nl.fontys.kwetter.exceptions;

public class UserDoesntExist extends Exception {

    public UserDoesntExist() {
    }

    public UserDoesntExist(String message) {
        super(message);
    }
}
