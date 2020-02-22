package br.alan.springcompanymeetingagenda.web.controllers.handlers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import br.alan.springcompanymeetingagenda.web.controllers.models.InputDataValidationErrorResponse;

/**
 * ConstraintViolationExceptionHandler
 * 
 * Custom implementation for object validation exceptions.
 * 
 * @author Alan Alonso
 */
@SuppressWarnings({"deprecation"})
@ControllerAdvice
public class InputDataValidationHandler {

    /**
     * Handle {@link javax.validation.ConstraintViolationException}. Generates JSON response with
     * errors.
     * 
     * @param constraintViolationException thrown exception
     * @param request                      current served request
     * @return JSON response with detected errors
     * @throws MalformedURLException {@see java.net.MalformedURLException}
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<InputDataValidationErrorResponse> constraintViolationExceptionHandler(
            ConstraintViolationException constraintViolationException, ServletWebRequest request)
            throws MalformedURLException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);

        StringBuffer url = request.getRequest().getRequestURL();

        Map<String, String> errors = new HashMap<>();
        constraintViolationException.getConstraintViolations().forEach(cv -> {
            errors.put(cv.getPropertyPath().toString(), cv.getMessage());
        });

        InputDataValidationErrorResponse res =
                InputDataValidationErrorResponse.builder().errors(errors)
                        .message("Bad format for resource!").path(new URL(url.toString())).build();

        return new ResponseEntity<>(res, headers, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle {@link org.springframework.web.bind.MethodArgumentNotValidException}. Generates JSON
     * response with errors.
     * 
     * @param methodArgumentNotValidException thrown exception
     * @param request                         current served request
     * @return JSON response with detected errors
     * @throws MalformedURLException {@see java.net.MalformedURLException}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InputDataValidationErrorResponse> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException methodArgumentNotValidException,
            ServletWebRequest request) throws MalformedURLException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);

        StringBuffer url = request.getRequest().getRequestURL();

        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });

        InputDataValidationErrorResponse res =
                InputDataValidationErrorResponse.builder().errors(errors)
                        .message("Bad format for resource!").path(new URL(url.toString())).build();

        return new ResponseEntity<>(res, headers, HttpStatus.BAD_REQUEST);
    }
}
