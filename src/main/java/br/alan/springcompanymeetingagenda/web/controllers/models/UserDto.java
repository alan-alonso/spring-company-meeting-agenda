package br.alan.springcompanymeetingagenda.web.controllers.models;

import java.sql.Timestamp;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDto
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    // == fields ==
    private Long id;
    private String name;
    private Timestamp lastModifiedDate;
    private String modifiedBy;
    private String username;
    Collection<? extends GrantedAuthority> authorities;
}