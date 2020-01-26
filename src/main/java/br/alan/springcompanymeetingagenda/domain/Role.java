package br.alan.springcompanymeetingagenda.domain;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Role
 */
@Entity
@Table(name = "role", schema = "meeting")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class Role extends BaseEntity {

    // == constructors ==
    @Builder
    public Role(Long id, String name, Timestamp createdDate, Timestamp lastModifiedDate) {
        super(id, name, createdDate, lastModifiedDate);
    }
}
