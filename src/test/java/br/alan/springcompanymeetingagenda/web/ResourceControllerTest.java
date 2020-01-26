package br.alan.springcompanymeetingagenda.web;

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
import org.springframework.test.web.servlet.MockMvc;
import br.alan.springcompanymeetingagenda.domain.Resource;
import br.alan.springcompanymeetingagenda.domain.ResourceType;
import br.alan.springcompanymeetingagenda.services.ResourceService;
import br.alan.springcompanymeetingagenda.utils.Mappings;
import br.alan.springcompanymeetingagenda.web.controllers.CRUDRestControllerTest;
import br.alan.springcompanymeetingagenda.web.controllers.ResourceController;

/**
 * ResourceControllerTest
 */
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ResourceController.class)
public class ResourceControllerTest extends CRUDRestControllerTest<Resource> {

    @MockBean
    ResourceService resourceService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    Resource resource;

    ResourceType resourceType = ResourceType.builder().id(1L).name("resourceTypeName")
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now())).build();

    @BeforeEach
    void setUp() {
        super.setApiEndPointPath(Mappings.RESOURCES_PATH);
        super.setMockMvc(this.mockMvc);
        super.setObjectMapper(this.objectMapper);
        super.setService(this.resourceService);
        this.resource = Resource.builder().id(1L).name("resourceName")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .resourceType(this.resourceType).build();
    }

    @AfterEach
    void tearDown() {
        reset(this.resourceService);
    }

    @DisplayName("getResources should call service method and return correct paged data")
    @Test
    void getResourcesTest() throws Exception {
        // arrange
        Page<Resource> pagedResources =
                new PageImpl<>(Stream.of(this.resource).collect(Collectors.toList()));

        super.listAllTest(pagedResources);
    }

    @DisplayName("getById should call service getById and return stored Resource")
    @Test
    void getResourceTest() throws Exception {
        super.getByIdTest(this.resource);
    }

    @DisplayName("getById should call service getById, throw NotFoundException and return object handled by ExceptionHandler")
    @Test
    void getResourceShouldThrow() throws Exception {
        super.getByIdShouldThrow(this.resource);
    }

    @DisplayName("create should call service and return new stored ResourceType")
    @Test
    void createResourceTypeTest() throws Exception {
        super.createTest(this.resource);
    }

    @DisplayName("update should call service update method and return correct modified Resource")
    @Test
    void updateResourceTest() throws Exception {
        super.updateTest(this.resource);
    }

    @DisplayName("update should call service update method, throw NotFoundException and return object handled by ExceptionHandler")
    @Test
    void updateResouceShouldThrow() throws Exception {
        super.updateShouldThrow(this.resource);
    }


    @DisplayName("delete should call repository delete method")
    @Test
    void deleteResourceTest() throws Exception {
        super.deleteTest();
    }

    @DisplayName("delete should call repository delete method and throw NotFoundException")
    @Test
    void deleteResourceShouldThrow() throws Exception {
        super.deleteShouldThrow();
    }
}
