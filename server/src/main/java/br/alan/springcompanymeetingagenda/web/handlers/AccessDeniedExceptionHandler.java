package br.alan.springcompanymeetingagenda.web.handlers;

import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import br.alan.springcompanymeetingagenda.web.models.BaseErrorResponse;

/**
 * AccessDeniedExceptionHandler
 * 
 * Custom implementation for access denied exceptions.
 * 
 * @author Alan Alonso
 */
@SuppressWarnings("deprecation")
@ControllerAdvice
public class AccessDeniedExceptionHandler {

    /**
     * Handle {@link org.springframework.security.access.AccessDeniedException}. Generates JSON
     * response with errors.
     * 
     * @param accessDeniedException thrown exception
     * @param request               current served request
     * @return JSON response with error object
     * @throws MalformedURLException {@see java.net.MalformedURLException}
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseErrorResponse> accessDeniedExceptionHandler(
            AccessDeniedException accessDeniedException, ServletWebRequest request)
            throws MalformedURLException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);

        StringBuffer url = request.getRequest().getRequestURL();

        BaseErrorResponse res =
                BaseErrorResponse.builder().message(HttpStatus.FORBIDDEN.getReasonPhrase())
                        .path(new URL(url.toString())).build();

        return new ResponseEntity<>(res, headers, HttpStatus.FORBIDDEN);
    }
}
