package br.alan.springcompanymeetingagenda.services;

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
}
