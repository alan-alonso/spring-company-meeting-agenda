package br.alan.springcompanymeetingagenda.web.controllers.models;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * InputDataValidationErrorResponse
 * 
 * Error response object for data binding / validation errors.
 * 
 * @author Alan Alonso
 */
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class InputDataValidationErrorResponse extends BaseErrorResponse {

    // == fields ==
    private Map<String, String> errors;
}
