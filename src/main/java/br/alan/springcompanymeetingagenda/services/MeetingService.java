package br.alan.springcompanymeetingagenda.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.alan.springcompanymeetingagenda.domain.Meeting;
import br.alan.springcompanymeetingagenda.repositories.MeetingRepository;
import br.alan.springcompanymeetingagenda.utils.BeanUtilsExtension;
import lombok.RequiredArgsConstructor;

/**
 * MeetingService
 */
@RequiredArgsConstructor
@Service
public class MeetingService implements CRUDService<Meeting> {

    // == fields ==
    private final MeetingRepository meetingRepository;

    @Override
    public Page<Meeting> listAll(Pageable pageable) {
        return this.meetingRepository.findAll(pageable);
    }

    @Override
    public Meeting getById(Long id) throws NotFoundException {
        return this.meetingRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Meeting create(Meeting object) {
        return this.meetingRepository.save(object);
    }

    @Override
    public Meeting update(Long id, Meeting object) throws NotFoundException {
        // retrieve stored Meeting
        Meeting storedMeeting =
                this.meetingRepository.findById(id).orElseThrow(NotFoundException::new);

        // merge the incoming modified meeting with the stored one while preserving non-null
        // properties from the stored object whose values are null in the incoming.
        BeanUtilsExtension.copyProperties(object, storedMeeting,
                BeanUtilsExtension.getNullPropertyNames(object));

        return this.meetingRepository.save(storedMeeting);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        // retrieve stored Meeting
        Meeting storedMeeting =
                this.meetingRepository.findById(id).orElseThrow(NotFoundException::new);

        this.meetingRepository.delete(storedMeeting);
    }


}
