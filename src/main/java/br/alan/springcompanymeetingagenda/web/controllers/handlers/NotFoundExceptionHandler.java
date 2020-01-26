package br.alan.springcompanymeetingagenda.web.controllers.handlers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * NotFoundExceptionHandler
 */
@SuppressWarnings({"deprecation"})
@ControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFoundExceptionHandler(ServletWebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);

        Map<String, Object> data = new HashMap<>();
        StringBuffer url = request.getRequest().getRequestURL();
        Integer id = Integer.parseInt(url.substring(url.lastIndexOf("/") + 1, url.length()));

        data.put("message", String.format("Resource with the given ID %d couldn't be found!", id));
        data.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
        data.put("path", url);
        return new ResponseEntity<>(data, headers, HttpStatus.NOT_FOUND);
    }
}
