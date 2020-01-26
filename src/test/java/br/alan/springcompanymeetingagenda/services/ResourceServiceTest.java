package br.alan.springcompanymeetingagenda.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import br.alan.springcompanymeetingagenda.domain.Resource;
import br.alan.springcompanymeetingagenda.domain.ResourceType;
import br.alan.springcompanymeetingagenda.repositories.ResourceRepository;

/**
 * ResourceServiceTest
 */
@ExtendWith(MockitoExtension.class)
public class ResourceServiceTest extends CRUDServiceTest<Resource> {

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
        super.setRepository(this.resourceRepository);
        super.setService(this.resourceService);
        this.resource = Resource.builder().id(1L).name("resourceName")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .resourceType(this.resourceType).build();
    }

    @DisplayName("getById should call repository and return stored Resource")
    @Test
    void getResourceTypeByIdTest() throws NotFoundException {
        super.getByIdTest(this.resource);
    }

    @DisplayName("getById should throw NotFoundException")
    @Test
    void getResourceByIdShouldThrow() throws NotFoundException {
        super.getByIdShouldThrow();
    }

    @DisplayName("listAll should return correct paged data")
    @Test
    void getResourcesTest() {
        super.listAllTest();
    }

    @DisplayName("create should return new stored Resource")
    @Test
    void createResourceTest() {
        super.createTest(this.resource);
    }

    @DisplayName("update should return correct modified Resource")
    @Test
    void updateResourceTest() throws NotFoundException {
        // arrange
        Resource modifiedResource = Resource.builder().name("modified").build();

        Resource expectedMergedResource = Resource.builder().id(this.resource.getId())
                .name(modifiedResource.getName()).createdDate(this.resource.getCreatedDate())
                .lastModifiedDate(this.resource.getLastModifiedDate()).build();

        super.updateTest(modifiedResource, this.resource, expectedMergedResource);
    }

    @DisplayName("update should throw NotFoundException")
    @Test
    void updateResourceShouldThrow() throws NotFoundException {
        super.updateShouldThrow(this.resource);
    }

    @DisplayName("delete should call repository delete method")
    @Test
    void deleteResourceTest() throws NotFoundException {
        super.deleteTest(this.resource);
    }

    @DisplayName("delete should throw NotFoundException")
    @Test
    void deleteResourceShouldThrow() throws NotFoundException {
        super.deleteShouldThrow();
    }
}
