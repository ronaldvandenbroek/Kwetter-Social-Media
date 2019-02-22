package nl.fontys.kwetter.exceptions;

public class KwetterDoesntExist extends Exception {

    public KwetterDoesntExist() {
    }

    public KwetterDoesntExist(String message) {
        super(message);
    }
}
