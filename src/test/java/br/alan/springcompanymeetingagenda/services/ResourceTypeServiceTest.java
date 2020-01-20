package br.alan.springcompanymeetingagenda.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
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
 */
@ExtendWith(MockitoExtension.class)
public class ResourceTypeServiceTest {

    @Mock
    ResourceTypeRepository resourceTypeRepository;

    @InjectMocks
    ResourceTypeServiceImpl resourceTypeService;

    ResourceType resourceType;

    @BeforeEach
    void setUp() {
        this.resourceType = ResourceType.builder().id(1L).name("name")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now())).build();
    }
    
    // TODO: Get one resourceType (correct and throw)

    // TODO: Get all (paged) resourceTypes

    @DisplayName("Create ResourceType Test should call repository and return created entity")
    @Test
    void createResourceTypeTest() {
        // arrange
        when(this.resourceTypeRepository.save(any(ResourceType.class))).thenReturn(resourceType);

        // act
        ResourceType resourceType = this.resourceTypeService.createResourceType(this.resourceType);

        // assert
        verify(this.resourceTypeRepository).save(this.resourceType);
        assertEquals(this.resourceType, resourceType);
    }

    @DisplayName("updateResourceType should return correct modified resource type")
    @Test
    void updateResourceTypeTest() throws NotFoundException {
        // arrange
        ResourceType modifiedResourceType = ResourceType.builder().name("modified").build();

        ResourceType expectedMergedResourceType = ResourceType.builder()
                .id(this.resourceType.getId()).name(modifiedResourceType.getName())
                .createdDate(this.resourceType.getCreatedDate())
                .lastModifiedDate(this.resourceType.getLastModifiedDate()).build();

        when(this.resourceTypeRepository.findById(resourceType.getId()))
                .thenReturn(Optional.of(resourceType));
        when(this.resourceTypeRepository.save(any(ResourceType.class)))
                .thenReturn(expectedMergedResourceType);

        // act
        ResourceType actualMergedResourceType = this.resourceTypeService
                .updateResourceType(this.resourceType.getId(), modifiedResourceType);

        // assert
        verify(this.resourceTypeRepository).findById(this.resourceType.getId());
        verify(this.resourceTypeRepository).save(expectedMergedResourceType);
        assertEquals(expectedMergedResourceType, actualMergedResourceType);
    }

    @DisplayName("updateResourceType should throw NotFoundException")
    @Test
    void updateResourceTypeTestShouldThrow() {
        // arrange
        when(this.resourceTypeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        // act / assert
        assertThrows(NotFoundException.class, () -> this.resourceTypeService
                .updateResourceType(this.resourceType.getId(), this.resourceType));
    }

    @DisplayName("deleteResourceType should call repository delete method")
    @Test
    void deleteResourceTypeTest() throws NotFoundException {
        // assert
        when(this.resourceTypeRepository.findById(this.resourceType.getId()))
                .thenReturn(Optional.ofNullable(this.resourceType));

        // act
        this.resourceTypeService.deleteResourceType(this.resourceType.getId());

        // assert
        verify(this.resourceTypeRepository).delete(any(ResourceType.class));
    }

    @DisplayName("deleteResourceType should throw NotFoundException")
    @Test
    void deleteResourceTypeShouldThrow() throws NotFoundException {
        // arrange
        when(this.resourceTypeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        // act / assert
        assertThrows(NotFoundException.class,
                () -> this.resourceTypeService.deleteResourceType(this.resourceType.getId()));
    }
}
