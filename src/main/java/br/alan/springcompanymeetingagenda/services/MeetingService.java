package br.alan.springcompanymeetingagenda.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import br.alan.springcompanymeetingagenda.domain.Meeting;
import br.alan.springcompanymeetingagenda.repositories.MeetingRepository;

/**
 * MeetingService
 * 
 * @author Alan Alonso
 */
@Service
public class MeetingService extends CRUDServiceImpl<Meeting, MeetingRepository> {

    public MeetingService(MeetingRepository repository) {
        super(repository);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        super.delete(id);
    }

    @Override
    public Meeting update(Long id, Meeting object) throws NotFoundException {
        return super.update(id, object);
    }
}
