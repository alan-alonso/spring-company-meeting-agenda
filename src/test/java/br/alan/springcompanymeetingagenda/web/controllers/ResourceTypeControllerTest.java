package br.alan.springcompanymeetingagenda.web.controllers;

import static org.mockito.Mockito.reset;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import br.alan.springcompanymeetingagenda.domain.ResourceType;
import br.alan.springcompanymeetingagenda.services.ResourceTypeService;
import br.alan.springcompanymeetingagenda.utils.Mappings;

/**
 * ResourceTypeControllerTest
 * 
 * Test class for {@link ResourceType}.
 * 
 * @author Alan Alonso
 */
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ResourceTypeController.class)
public class ResourceTypeControllerTest extends CRUDRestControllerTest<ResourceType> {

    @MockBean
    ResourceTypeService resourceTypeService;
    
    @MockBean
    UserDetailsService userDetailsService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    ResourceType resourceType;

    @BeforeEach
    void setUp() {
        super.setApiEndPointPath(Mappings.RESOURCE_TYPES_PATH);
        super.setMockMvc(this.mockMvc);
        super.setObjectMapper(this.objectMapper);
        super.setService(this.resourceTypeService);
        this.resourceType = ResourceType.builder().id(1L).name("name")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now())).build();
    }

    @AfterEach
    void tearDown() {
        reset(this.resourceTypeService);
    }

    @DisplayName("getResourceTypes should call service method and return correct paged data")
    @Test
    void getResourceTypesTest() throws Exception {
        // arrange
        Page<ResourceType> pagedResourceTypes =
                new PageImpl<>(Stream.of(this.resourceType).collect(Collectors.toList()));

        super.listAllTest(pagedResourceTypes);
    }

    @DisplayName("getById should call service getById and return stored ResourceType")
    @Test
    void getResourceTypeTest() throws Exception {
        super.getByIdTest(this.resourceType);
    }

    @DisplayName("getById should call service getById, throw NotFoundException and return object handled by ExceptionHandler")
    @Test
    void getResourceTypeShouldThrow() throws Exception {
        super.getByIdShouldThrow(this.resourceType);
    }

    @DisplayName("create should call service and return new stored ResourceType")
    @Test
    void createResourceTypeTest() throws Exception {
        super.createTest(this.resourceType);
    }

    @DisplayName("update should call service update method and return correct modified ResourceType")
    @Test
    void updateResourceTypeTest() throws Exception {
        super.updateTest(this.resourceType);
    }

    @DisplayName("update should call service update method, throw NotFoundException and return object handled by ExceptionHandler")
    @Test
    void updateResouceTypeShouldThrow() throws Exception {
        super.updateShouldThrow(this.resourceType);
    }


    @DisplayName("delete should call repository delete method")
    @Test
    void deleteResourceTypeTest() throws Exception {
        super.deleteTest();
    }

    @DisplayName("delete should call repository delete method and throw NotFoundException")
    @Test
    void deleteResourceTypeShouldThrow() throws Exception {
        super.deleteShouldThrow();
    }
}
