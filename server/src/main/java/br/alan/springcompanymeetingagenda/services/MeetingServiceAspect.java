package br.alan.springcompanymeetingagenda.services;

import java.util.Collection;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import br.alan.springcompanymeetingagenda.domain.Meeting;
import br.alan.springcompanymeetingagenda.repositories.MeetingRepository;
import lombok.RequiredArgsConstructor;

/**
 * MeetingServiceAspect
 * 
 * Advice methods to authorize update and deletion of meetings by users in the
 * {@link MeetingService}.
 */
@Aspect
@RequiredArgsConstructor
@Component
public class MeetingServiceAspect {

    // == fields ==
    private final MeetingRepository meetingRepository;

    // == public methods ==
    /**
     * Authorize the deletion of a meeting by an user.
     * 
     * @param id
     * @throws AccessDeniedException if user isn't authorized to perform the action
     * @throws NotFoundException     if meeting couldn't be found
     */
    @Before("execution(* br.alan.springcompanymeetingagenda.services.MeetingService.delete(..)) && args(id)")
    public void authorizeDeleteMeeting(Long id) throws AccessDeniedException, NotFoundException {
        if (!this.authorizeMeetingEdition(id)) {
            throw new AccessDeniedException(HttpStatus.FORBIDDEN.getReasonPhrase());
        }
    }

    /**
     * Authorize the update of a meeting by an user.
     * 
     * @param id
     * @throws AccessDeniedException if user isn't authorized to perform the action
     * @throws NotFoundException     if meeting couldn't be found
     */
    @Before("execution(* br.alan.springcompanymeetingagenda.services.MeetingService.update(..)) && args(id)")
    public void authorizeUpdateMeeting(Long id) throws AccessDeniedException, NotFoundException {
        if (!this.authorizeMeetingEdition(id)) {
            throw new AccessDeniedException(HttpStatus.FORBIDDEN.getReasonPhrase());
        }
    }

    // == private methods ==
    /**
     * Returns true if user is creator of meeting or is admin.
     * 
     * @param meetingId
     * @return true if user is creator of meeting or is admin.
     * @throws NotFoundException if resource couldn't be found
     */
    private boolean authorizeMeetingEdition(Long meetingId) throws NotFoundException {
        String username =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authorities.stream().filter(authority -> authority.getAuthority().equals("ROLE_ADMIN"))
                .count() > 0) {
            return true;
        }

        Meeting meeting =
                this.meetingRepository.findById(meetingId).orElseThrow(NotFoundException::new);
        return meeting.getCreatedBy() != null && meeting.getCreatedBy().equals(username);
    }
}
