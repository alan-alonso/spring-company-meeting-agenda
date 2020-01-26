package br.alan.springcompanymeetingagenda.web.controllers;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import br.alan.springcompanymeetingagenda.domain.Meeting;
import br.alan.springcompanymeetingagenda.domain.Resource;
import br.alan.springcompanymeetingagenda.services.MeetingService;
import br.alan.springcompanymeetingagenda.utils.Mappings;

/**
 * MeetingControllerTest
 */
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(MeetingController.class)
public class MeetingControllerTest extends CRUDRestControllerTest<Meeting> {

    @MockBean
    MeetingService meetingService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    Meeting meeting;

    Resource resource = Resource.builder().id(1L).name("resourceName")
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now())).build();

    @BeforeEach
    void setUp() {
        super.setApiEndPointPath(Mappings.MEETINGS_PATH);
        super.setMockMvc(this.mockMvc);
        super.setObjectMapper(this.objectMapper);
        super.setService(this.meetingService);
        this.meeting = Meeting.builder().id(1L).name("meetingName")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .start(Timestamp.valueOf(LocalDateTime.now().plus(1L, ChronoUnit.MINUTES)))
                .end(Timestamp.valueOf(LocalDateTime.now().plus(1L, ChronoUnit.MINUTES)))
                .resources(Stream.of(this.resource).collect(Collectors.toSet())).build();
    }

    @AfterEach
    void tearDown() {
        reset(this.meetingService);
    }

    @DisplayName("getMeetings should call service method and return correct paged data")
    @Test
    void getMeetingsTest() throws Exception {
        // arrange
        Page<Meeting> pagedMeetings =
                new PageImpl<>(Stream.of(this.meeting).collect(Collectors.toList()));

        super.listAllTest(pagedMeetings);
    }

    @DisplayName("getById should call service getById and return stored Meeting")
    @Test
    void getMeetingTest() throws Exception {
        super.getByIdTest(this.meeting);
    }

    @DisplayName("getById should call service getById, throw NotFoundException and return object handled by ExceptionHandler")
    @Test
    void getMeetingShouldThrow() throws Exception {
        super.getByIdShouldThrow(this.meeting);
    }

    @DisplayName("create should call service and return new stored Meeting")
    @Test
    void createMeetingTest() throws Exception {
        super.createTest(this.meeting);
    }

    @SuppressWarnings({"deprecation"})
    @DisplayName("create throw MethodArgumentNotValidException and return object handled by ExceptionHandler")
    @Test
    void createMeetingShouldThrowConstraintValidationException()
            throws JsonProcessingException, Exception {
        // arrange
        this.meeting.setStart(Timestamp.valueOf(LocalDateTime.now().minus(1L, ChronoUnit.MINUTES)));
        this.meeting.setEnd(Timestamp.valueOf(LocalDateTime.now().minus(1L, ChronoUnit.MINUTES)));

        // act / assert
        this.mockMvc
                .perform(post(Mappings.MEETINGS_PATH).contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(this.meeting)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.errors.length()", Is.is(2)))
                .andExpect(jsonPath("$.errors.start", IsNot.not(IsNull.nullValue())))
                .andExpect(jsonPath("$.errors.end", IsNot.not(IsNull.nullValue())));
    }

    @DisplayName("update should call service update method and return correct modified Meeting")
    @Test
    void updateMeetingTest() throws Exception {
        super.updateTest(this.meeting);
    }

    @DisplayName("update should call service update method, throw NotFoundException and return object handled by ExceptionHandler")
    @Test
    void updateResouceTypeShouldThrow() throws Exception {
        super.updateShouldThrow(this.meeting);
    }

    @SuppressWarnings({"deprecation"})
    @DisplayName("update throw MethodArgumentNotValidException and return object handled by ExceptionHandler")
    @Test
    void updateMeetingShouldThrowConstraintValidationException()
            throws JsonProcessingException, Exception {
        // arrange
        this.meeting.setStart(Timestamp.valueOf(LocalDateTime.now().minus(1L, ChronoUnit.MINUTES)));
        this.meeting.setEnd(Timestamp.valueOf(LocalDateTime.now().minus(1L, ChronoUnit.MINUTES)));

        // act / assert
        this.mockMvc
                .perform(put(Mappings.MEETINGS_PATH + "/{id}", this.meeting.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(this.meeting)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.errors.length()", Is.is(2)))
                .andExpect(jsonPath("$.errors.start", IsNot.not(IsNull.nullValue())))
                .andExpect(jsonPath("$.errors.end", IsNot.not(IsNull.nullValue())));
    }

    @DisplayName("delete should call repository delete method")
    @Test
    void deleteMeetingTest() throws Exception {
        super.deleteTest();
    }

    @DisplayName("delete should call repository delete method and throw NotFoundException")
    @Test
    void deleteMeetingShouldThrow() throws Exception {
        super.deleteShouldThrow();
    }
}
