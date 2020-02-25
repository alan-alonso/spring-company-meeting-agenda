package br.alan.springcompanymeetingagenda.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import br.alan.springcompanymeetingagenda.domain.Meeting;
import br.alan.springcompanymeetingagenda.repositories.MeetingRepository;

/**
 * MeetingServiceAspectTest
 * 
 * Test class for {@link MeetingServiceAspect}.
 * 
 * @author Alan Alonso
 */
@ExtendWith(MockitoExtension.class)
public class MeetingServiceAspectTest {

    @Mock
    MeetingRepository meetingRepository;

    @InjectMocks
    MeetingServiceAspect meetingServiceAspect;

    private static final String USER = "USER";
    private static Authentication authentication;
    private static SecurityContext securityContext;
    private static List<GrantedAuthority> authorities;
    private static Meeting meeting;


    @BeforeAll
    static void beforeAll() {
        authorities = getListWithAdminAuthority();

        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(USER);
        doReturn(authorities).when(authentication).getAuthorities();
    }

    @BeforeEach
    void setUp() {
        authorities.clear();
        authorities.addAll(getListWithAdminAuthority());
        meeting = getValidMeeting();
    }

    private static Meeting getValidMeeting() {
        return Meeting.builder().createdBy(USER).build();
    }

    private static List<GrantedAuthority> getListWithAdminAuthority() {
        return Stream.of(new SimpleGrantedAuthority("ROLE_ADMIN")).collect(Collectors.toList());
    }

    @DisplayName("authorizeDeleteMeeting should throw AccessDeniedException, as user isn't either the creator or admin")
    @Test
    void authorizeDeleteMeetingShouldThrowAccessDenied() {
        // arrange
        when(this.meetingRepository.findById(anyLong())).thenReturn(Optional.ofNullable(meeting));

        // remove admin authority
        authorities.clear();

        // modify creator
        meeting.setCreatedBy("createdBy");

        // act / assert
        assertThrows(AccessDeniedException.class,
                () -> this.meetingServiceAspect.authorizeDeleteMeeting(1L));
    }

    @DisplayName("authorizeDeleteMeeting shouldn't throw AccessDeniedException, as, even though user isn't the creator, user is admin")
    @Test
    void authorizeDeleteMeetingShouldntThrowAccessDenied_UserAdmin()
            throws AccessDeniedException, NotFoundException {
        // arrange
        // modify creator
        meeting.setCreatedBy("createdBy");

        // act / assert
        this.meetingServiceAspect.authorizeDeleteMeeting(1L);
    }

    @DisplayName("authorizeDeleteMeeting shouldn't throw AccessDeniedException, as, even though user isn't admin, user is the creator")
    @Test
    void authorizeDeleteMeetingShouldntThrowAccessDenied_UserCreator()
            throws AccessDeniedException, NotFoundException {
        // arrange
        when(this.meetingRepository.findById(anyLong())).thenReturn(Optional.ofNullable(meeting));

        // remove admin authority
        authorities.clear();

        // act / assert
        this.meetingServiceAspect.authorizeDeleteMeeting(1L);
    }

    @DisplayName("authorizeUpdateMeeting should throw AccessDeniedException, as user isn't either the creator or admin")
    @Test
    void authorizeUpdateMeetingShouldThrowAccessDenied() {
        // arrange
        when(this.meetingRepository.findById(anyLong())).thenReturn(Optional.ofNullable(meeting));

        // remove admin authority
        authorities.clear();

        // modify creator
        meeting.setCreatedBy("createdBy");

        // act / assert
        assertThrows(AccessDeniedException.class,
                () -> this.meetingServiceAspect.authorizeUpdateMeeting(1L));
    }

    @DisplayName("authorizeUpdateMeeting shouldn't throw AccessDeniedException, as, even though user isn't the creator, user is admin")
    @Test
    void authorizeUpdateMeetingShouldntThrowAccessDenied_UserAdmin()
            throws AccessDeniedException, NotFoundException {
        // arrange
        // modify creator
        meeting.setCreatedBy("createdBy");

        // act / assert
        this.meetingServiceAspect.authorizeUpdateMeeting(1L);
    }

    @DisplayName("authorizeUpdateMeeting shouldn't throw AccessDeniedException, as, even though user isn't admin, user is the creator")
    @Test
    void authorizeUpdateMeetingShouldntThrowAccessDenied_UserCreator()
            throws AccessDeniedException, NotFoundException {
        // arrange
        when(this.meetingRepository.findById(anyLong())).thenReturn(Optional.ofNullable(meeting));

        // remove admin authority
        authorities.clear();

        // act / assert
        this.meetingServiceAspect.authorizeUpdateMeeting(1L);
    }
}
