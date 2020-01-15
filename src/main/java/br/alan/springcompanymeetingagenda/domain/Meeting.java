package br.alan.springcompanymeetingagenda.domain;

import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Meeting
 */
@Entity
@Table(name = "meeting", schema = "meeting")
@Getter
@Setter
@NoArgsConstructor
public class Meeting extends BaseEntity {

    // == fields ==
    @Column(length = 500)
    private String description;

    private Timestamp start;

    private Timestamp end;

    @OneToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "meeting_resource", schema = "meeting",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private Set<Resource> resources;

    // == constructors ==
    @Builder
    public Meeting(Long id, String name, Timestamp createdDate, Timestamp lastModifiedDate,
            String description, Timestamp start, Timestamp end, Set<Resource> resources) {
        super(id, name, createdDate, lastModifiedDate);
        this.description = description;
        this.start = start;
        this.end = end;
        this.resources = resources;
    }
}
