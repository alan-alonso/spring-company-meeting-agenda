package br.alan.springcompanymeetingagenda.web.controllers.handlers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

/**
 * ConstraintViolationExceptionHandler
 */
@SuppressWarnings({"deprecation"})
@ControllerAdvice
public class InputDataValidationHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> constraintViolationExceptionHandler(
            ConstraintViolationException constraintViolationException, ServletWebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);

        Map<String, Object> data = new HashMap<>();
        StringBuffer url = request.getRequest().getRequestURL();

        Map<String, String> errors = new HashMap<>();
        constraintViolationException.getConstraintViolations().forEach(cv -> {
            errors.put(cv.getPropertyPath().toString(), cv.getMessage());
        });

        data.put("errors", errors);
        data.put("message", "Bad format for resource!");
        data.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
        data.put("path", url);
        return new ResponseEntity<>(data, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException methodArgumentNotValidException,
            ServletWebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);

        Map<String, Object> data = new HashMap<>();

        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });

        data.put("errors", errors);
        data.put("message", "Bad format for resource!");
        data.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
        data.put("path", request.getRequest().getRequestURL());
        return new ResponseEntity<>(data, headers, HttpStatus.BAD_REQUEST);
    }
}
