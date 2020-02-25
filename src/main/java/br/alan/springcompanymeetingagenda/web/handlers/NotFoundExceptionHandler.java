package br.alan.springcompanymeetingagenda.web.handlers;

import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import br.alan.springcompanymeetingagenda.web.models.BaseErrorResponse;

/**
 * NotFoundExceptionHandler
 * 
 * Custom implementation for requested resource(s) not found exceptions.
 */
@SuppressWarnings({"deprecation"})
@ControllerAdvice
public class NotFoundExceptionHandler {

    /**
     * Handle {@link org.springframework.data.crossstore.ChangeSetPersister.NotFoundException}.
     * Generates JSON response with errors.
     * 
     * @param request current served request
     * @return JSON response with error object
     * @throws MalformedURLException {@see java.net.MalformedURLException}
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseErrorResponse> notFoundExceptionHandler(ServletWebRequest request)
            throws MalformedURLException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);

        StringBuffer url = request.getRequest().getRequestURL();
        Integer id = Integer.parseInt(url.substring(url.lastIndexOf("/") + 1, url.length()));

        BaseErrorResponse res = BaseErrorResponse.builder()
                .message(String.format("Resource with the given ID %d couldn't be found!", id))
                .path(new URL(url.toString())).build();
        return new ResponseEntity<>(res, headers, HttpStatus.NOT_FOUND);
    }
}
