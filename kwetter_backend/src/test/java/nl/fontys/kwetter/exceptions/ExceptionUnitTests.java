package nl.fontys.kwetter.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ExceptionUnitTests {

    @Test
    void CouldNotDeleteModelException() {
        assertThrows(CouldNotDeleteModelException.class, () -> {throw new CouldNotDeleteModelException("Test");});
        assertThrows(FailedToAddLinksException.class, () -> {throw new FailedToAddLinksException("Test");});
        assertThrows(LoginException.class, () -> {throw new LoginException("Test");});
        assertThrows(ModelInvalidException.class, () -> {throw new ModelInvalidException("Test");});
        assertThrows(ModelNotFoundException.class, () -> {throw new ModelNotFoundException("Test");});
        assertThrows(NotImplementedException.class, () -> {throw new NotImplementedException("Test");});
        assertThrows(UsernameAlreadyExistsException.class, () -> {throw new UsernameAlreadyExistsException("Test");});
    }
}