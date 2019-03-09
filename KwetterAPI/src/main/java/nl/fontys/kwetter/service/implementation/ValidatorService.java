package nl.fontys.kwetter.service.implementation;

import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.service.IValidatorService;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class ValidatorService implements IValidatorService {
    private Validator validator;

    public ValidatorService() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public void validate(Object object) throws InvalidModelException {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        StringBuilder violationMessages = new StringBuilder();
        for (ConstraintViolation<Object> violation : violations) {
            violationMessages.append(violation.getMessage()).append(" ");
        }
        if (violationMessages.length() != 0) {
            throw new InvalidModelException(violationMessages.toString());
        }
    }
}
