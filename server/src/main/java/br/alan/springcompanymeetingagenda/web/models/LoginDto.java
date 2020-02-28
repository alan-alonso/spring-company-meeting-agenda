package br.alan.springcompanymeetingagenda.web.models;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoginDto
 * 
 * Login data transfer object for authentication.
 * 
 * @author Alan Alonso
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    // == fields ==
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
