package br.alan.springcompanymeetingagenda.domain;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Resource
 */
@Entity
@Table(name = "resource", schema = "meeting")
@Getter
@Setter
@NoArgsConstructor
public class Resource extends BaseEntity {

    // == fields ==
    @OneToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "resource_type_id")
    private ResourceType resourceType;

    // == constructors ==
    @Builder
    public Resource(Long id, String name, Timestamp createdDate, Timestamp lastModifiedDate,
            ResourceType resourceType) {
        super(id, name, createdDate, lastModifiedDate);
        this.resourceType = resourceType;
    }
}
