package br.alan.springcompanymeetingagenda.web.models;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SuccessfulLoginResponse
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuccessfulLoginResponse {

    // == fields ==
    private Timestamp expiresIn;
}
