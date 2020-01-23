package br.alan.springcompanymeetingagenda.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import br.alan.springcompanymeetingagenda.domain.Meeting;
import br.alan.springcompanymeetingagenda.domain.Resource;
import br.alan.springcompanymeetingagenda.repositories.MeetingRepository;

/**
 * MeetingServiceTest
 */
@ExtendWith(MockitoExtension.class)
public class MeetingServiceTest extends CRUDServiceTest<Meeting> {

    @Mock
    MeetingRepository meetingRepository;

    @InjectMocks
    MeetingService meetingService;

    Meeting meeting;

    Resource resource = Resource.builder().id(1L).name("resourceName")
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now())).build();


    @BeforeEach
    void setUp() {
        super.setRepository(this.meetingRepository);
        super.setService(this.meetingService);
        this.meeting = Meeting.builder().id(1L).name("meetingName")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .resources(Stream.of(resource).collect(Collectors.toSet())).build();
    }

    @DisplayName("getById should call repository and return stored Meeting")
    @Test
    void getResourceTypeByIdTest() throws NotFoundException {
        super.getByIdTest(this.meeting);
    }

    @DisplayName("getById should call repository and return stored Meeting")
    @Test
    void getResourceByIdShouldThrow() throws NotFoundException {
        super.getByIdShouldThrow();
    }

    @DisplayName("listAll should return correct paged data")
    @Test
    void getResourcesTest() {
        super.listAllTest();
    }

    @DisplayName("create should return new stored Meeting")
    @Test
    void createResourceTest() {
        super.createTest(this.meeting);
    }

    @DisplayName("update should return correct modified Meeting")
    @Test
    void updateResourceTest() throws NotFoundException {
        // arrange
        Meeting modifiedMeeting = Meeting.builder().name("modified").build();

        Meeting expectedMergedMeeting = Meeting.builder().id(this.meeting.getId())
                .name(modifiedMeeting.getName()).createdDate(this.meeting.getCreatedDate())
                .lastModifiedDate(this.meeting.getLastModifiedDate()).build();

        super.updateTest(modifiedMeeting, this.meeting, expectedMergedMeeting);
    }

    @DisplayName("update should throw NotFoundException")
    @Test
    void updateResourceShouldThrow() throws NotFoundException {
        super.updateShouldThrow(this.meeting);
    }

    @DisplayName("delete should call repository delete method")
    @Test
    void deleteResourceTest() throws NotFoundException {
        super.deleteTest(this.meeting);
    }

    @DisplayName("delete should throw NotFoundException")
    @Test
    void deleteResourceShouldThrow() throws NotFoundException {
        super.deleteShouldThrow();
    }
}
