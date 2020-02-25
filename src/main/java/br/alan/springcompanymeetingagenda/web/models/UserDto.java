package br.alan.springcompanymeetingagenda.web.models;

import java.sql.Timestamp;
import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import org.springframework.security.core.GrantedAuthority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * UserDto
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    // == fields ==
    @JsonProperty(access = Access.READ_ONLY)
    private Long id;


    @Size(max = 100)
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = Access.READ_ONLY)
    private Timestamp lastModifiedDate;

    @JsonProperty(access = Access.READ_ONLY)
    private String modifiedBy;

    @Size(max = 20)
    @NotBlank
    private String username;

    Collection<? extends GrantedAuthority> authorities;
}
