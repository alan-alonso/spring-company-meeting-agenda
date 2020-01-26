package br.alan.springcompanymeetingagenda.web.controllers.models;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoginDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    // == fields ==
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
