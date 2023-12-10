package effectiveMobile.com.taskManagementSystem.exception;

import effectiveMobile.com.taskManagementSystem.constants.Errors;
import effectiveMobile.com.taskManagementSystem.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;

@RestControllerAdvice
public class ExceptionTranslator {
    
    @ExceptionHandler(TMSDeleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleTMSDeleteException(TMSDeleteException ex) {
        return proceedFieldsErrors(ex, Errors.REST.DELETE_ERROR_MESSAGE, ex.getMessage());
    }
    
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDto handleAuthException(AuthenticationException ex) {
        return proceedFieldsErrors(ex, Errors.REST.AUTH_ERROR_MESSAGE, ex.getMessage());
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDto handleAuthException(AccessDeniedException ex) {
        return proceedFieldsErrors(ex, Errors.REST.AUTH_ERROR_MESSAGE, ex.getMessage());
    }
    
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleAuthException(NotFoundException ex) {
        return proceedFieldsErrors(ex, Errors.REST.NOT_FOUND_ERROR_MESSAGE, ex.getMessage());
    }

    private ErrorDto proceedFieldsErrors(Exception ex,
                                         String error,
                                         String description) {
        ErrorDto errorDTO = new ErrorDto(error, description);
        errorDTO.add(ex.getClass().getName(), "", errorDTO.getMessage());
        return errorDTO;
    }
}
