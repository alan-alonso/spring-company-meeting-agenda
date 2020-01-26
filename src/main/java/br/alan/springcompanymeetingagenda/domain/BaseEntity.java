package br.alan.springcompanymeetingagenda.domain;

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

    @UpdateTimestamp
    @JsonProperty(access = Access.READ_ONLY)
    private Timestamp lastModifiedDate;
}
