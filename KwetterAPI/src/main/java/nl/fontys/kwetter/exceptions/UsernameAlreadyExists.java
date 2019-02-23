package nl.fontys.kwetter.exceptions;

public class UsernameAlreadyExists extends Exception {
    public UsernameAlreadyExists() {
    }

    public UsernameAlreadyExists(String message) {
        super(message);
    }
}
