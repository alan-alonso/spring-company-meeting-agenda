package br.alan.springcompanymeetingagenda.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.alan.springcompanymeetingagenda.domain.Resource;
import br.alan.springcompanymeetingagenda.domain.ResourceType;
import br.alan.springcompanymeetingagenda.repositories.ResourceRepository;

/**
 * ResourceServiceTest
 */
@ExtendWith(MockitoExtension.class)
public class ResourceServiceTest {

    @Mock
    ResourceRepository resourceRepository;

    @InjectMocks
    ResourceService resourceService;

    Resource resource;

    ResourceType resourceType = ResourceType.builder().id(1L).name("resourceTypeName")
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now())).build();

    @BeforeEach
    void setUp() {
        this.resource = Resource.builder().id(1L).name("resourceName")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .resourceType(this.resourceType).build();
    }

    // TODO: Creation Test

    // TODO: Update tests (throw and correct)

    // TODO: Delete tests (throw and correct)
}
