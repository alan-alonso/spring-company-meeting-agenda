package br.alan.springcompanymeetingagenda.domain;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ResourceType
 */
@Entity
@Table(name = "resource_type", schema = "meeting")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class ResourceType extends BaseEntity {

    // == constructors ==
    @Builder
    public ResourceType(Long id, String name, Timestamp createdDate, Timestamp lastModifiedDate,
            String createdBy, String modifiedBy) {
        super(id, name, createdDate, lastModifiedDate, createdBy, modifiedBy);
    }
}
