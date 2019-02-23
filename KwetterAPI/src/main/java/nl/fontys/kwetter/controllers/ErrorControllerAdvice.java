package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@ControllerAdvice
public class ErrorControllerAdvice {

    @ResponseBody
    @ExceptionHandler(CannotLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String cannotLoginException(CannotLoginException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidModelException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidModelException(InvalidModelException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(KwetterDoesntExist.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String kwetterDoesntExist(KwetterDoesntExist ex) {
        return ex.getMessage();
    }


    @ResponseBody
    @ExceptionHandler(UserDoesntExist.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userDoesntExist(UserDoesntExist ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UsernameAlreadyExists.class)
    @ResponseStatus(HttpStatus.GONE)
    public String usernameAlreadyExists(UsernameAlreadyExists ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NotImplementedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String notImplementedException(NotImplementedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String exception(Exception ex) {
        return ex.getMessage();
    }
}