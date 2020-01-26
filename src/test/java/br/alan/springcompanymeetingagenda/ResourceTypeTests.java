package br.alan.springcompanymeetingagenda;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import br.alan.springcompanymeetingagenda.domain.ResourceType;

/**
 * ResourceTypeTests
 */
@Tag("resourcetype")
public abstract class ResourceTypeTests {

    // == fields ==
    protected ResourceType resourceType;

    @BeforeEach
    void setUp() {
        this.resourceType = ResourceType.builder().id(1L).name("name")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now())).build();
    }
}
