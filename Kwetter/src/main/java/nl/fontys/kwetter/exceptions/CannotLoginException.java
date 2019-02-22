package nl.fontys.kwetter.exceptions;

public class CannotLoginException extends Exception {

    public CannotLoginException(){}

    public CannotLoginException(String message){
        super(message);
    }
}
