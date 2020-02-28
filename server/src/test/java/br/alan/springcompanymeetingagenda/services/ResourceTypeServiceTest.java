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
import br.alan.springcompanymeetingagenda.domain.ResourceType;
import br.alan.springcompanymeetingagenda.repositories.ResourceTypeRepository;

/**
 * ResourceTypeServiceTest
 * 
 * Test class for {@link ResourceTypeService}.
 * 
 * @author Alan Alonso
 */
@ExtendWith(MockitoExtension.class)
public class ResourceTypeServiceTest extends CRUDServiceTest<ResourceType> {

    @Mock
    ResourceTypeRepository resourceTypeRepository;

    @InjectMocks
    ResourceTypeService resourceTypeService;

    ResourceType resourceType;

    @BeforeEach
    void setUp() {
        super.setRepository(this.resourceTypeRepository);
        super.setService(this.resourceTypeService);
        this.resourceType = ResourceType.builder().id(1L).name("name")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now())).build();
    }

    @DisplayName("listAll should return correct paged data")
    @Test
    void getResourcesTest() {
        super.listAllTest();
    }

    @DisplayName("getById should call repository and return stored ResourceType")
    @Test
    void getResourceTypeByIdTest() throws NotFoundException {
        super.getByIdTest(this.resourceType);
    }

    @DisplayName("getById should throw NotFoundException")
    @Test
    void getResourceTypeByIdShouldThrow() throws NotFoundException {
        super.getByIdShouldThrow();
    }


    @DisplayName("create should return new stored ResourceType")
    @Test
    void createResourceTypeTest() {
        super.createTest(this.resourceType);
    }

    @DisplayName("update should return correct modified resource type")
    @Test
    void updateResourceTypeTest() throws NotFoundException {
        // arrange
        ResourceType modifiedResourceType = ResourceType.builder().name("modified").build();

        ResourceType expectedMergedResourceType = ResourceType.builder()
                .id(this.resourceType.getId()).name(modifiedResourceType.getName())
                .createdDate(this.resourceType.getCreatedDate())
                .lastModifiedDate(this.resourceType.getLastModifiedDate()).build();

        super.updateTest(modifiedResourceType, this.resourceType, expectedMergedResourceType);
    }

    @DisplayName("update should throw NotFoundException")
    @Test
    void updateResourceTypeShouldThrow() throws NotFoundException {
        super.updateShouldThrow(this.resourceType);
    }

    @DisplayName("delete should call repository delete method")
    @Test
    void deleteResourceTypeTest() throws NotFoundException {
        super.deleteTest(this.resourceType);
    }

    @DisplayName("delete should throw NotFoundException")
    @Test
    void deleteResourceTypeShouldThrow() throws NotFoundException {
        super.deleteShouldThrow();
    }

}
