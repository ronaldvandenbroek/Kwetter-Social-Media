package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.ModelInvalidException;

public interface IValidatorService {
    void validate(Object object) throws ModelInvalidException;
}
