package br.alan.springcompanymeetingagenda.domain;

import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * User
 */
@Entity
@Table(name = "user", schema = "meeting")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class User extends BaseEntity {

    // == fields ==
    private String username;

    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_user", schema = "meeting", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    private Boolean enabled = true;

    @Column(insertable = false)
    @JsonProperty(access = Access.READ_ONLY)
    private String forgotPasswordToken;

    @Column(insertable = false)
    @JsonProperty(access = Access.READ_ONLY)
    private Timestamp forgotPasswordTokenExpirationDate;

    // == constructors
    @Builder
    public User(Long id, String name, Timestamp createdDate, Timestamp lastModifiedDate,
            Set<Role> roles, String username, String password) {
        super(id, name, createdDate, lastModifiedDate);
        this.roles = roles;
        this.username = username;
        this.password = password;
    }
}
