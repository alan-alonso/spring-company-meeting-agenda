package br.alan.springcompanymeetingagenda.domain;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.springframework.security.core.context.SecurityContextHolder;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

/**
 * BaseEntity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class BaseEntity {

    // == fields ==
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigserial", insertable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(updatable = false)
    @CreationTimestamp
    @JsonProperty(access = Access.READ_ONLY)
    private Timestamp createdDate;

    @Column(name = "modified_date")
    @UpdateTimestamp
    @JsonProperty(access = Access.READ_ONLY)
    private Timestamp lastModifiedDate;

    @Column(name = "created_by", length = 20)
    @JsonProperty(access = Access.READ_ONLY)
    private String createdBy;

    @Column(name = "modified_by", length = 20)
    @JsonProperty(access = Access.READ_ONLY)
    private String modifiedBy;

    @PrePersist
    private void prePersist() {
        this.createdBy =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    @PreUpdate
    private void preUpdate() {
        this.lastModifiedDate = Timestamp.valueOf(LocalDateTime.now());
        this.modifiedBy =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
