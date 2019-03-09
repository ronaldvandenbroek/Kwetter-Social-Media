package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.InvalidModelException;

public interface IValidatorService {
    void validate(Object object) throws InvalidModelException;
}
