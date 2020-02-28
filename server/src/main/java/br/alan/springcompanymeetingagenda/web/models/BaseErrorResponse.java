package br.alan.springcompanymeetingagenda.web.models;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * BaseErrorResponse
 * 
 * Base response object for exception handlers.
 * 
 * @author Alan Alonso
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseErrorResponse {

    // == fields ==
    private String message;
    private URL path;

    private final Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
}
