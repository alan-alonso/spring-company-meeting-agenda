package br.alan.springcompanymeetingagenda.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Meeting
 */
@Entity
@Table(name = "meeting", schema = "meeting")
@BatchSize(size = 25)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, exclude = "resources")
public class Meeting extends BaseEntity {

    // == fields ==
    @Column(length = 500)
    private String description;

    @NotNull
    @FutureOrPresent
    @Column(name = "start_date", nullable = false)
    private Timestamp start;

    @NotNull
    @FutureOrPresent
    @Column(name = "end_date", nullable = false)
    private Timestamp end;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "meeting_resource", schema = "meeting",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private Set<Resource> resources = new HashSet<>();

    // == constructors ==
    @Builder
    public Meeting(Long id, String name, Timestamp createdDate, Timestamp lastModifiedDate,
            String createdBy, String modifiedBy, String description,
            @NotNull @FutureOrPresent Timestamp start, @NotNull @FutureOrPresent Timestamp end,
            Set<Resource> resources) {
        super(id, name, createdDate, lastModifiedDate, createdBy, modifiedBy);
        this.description = description;
        this.start = start;
        this.end = end;
        this.resources = resources;
    }
}
