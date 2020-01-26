package br.alan.springcompanymeetingagenda.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.alan.springcompanymeetingagenda.domain.Meeting;
import br.alan.springcompanymeetingagenda.services.MeetingService;
import br.alan.springcompanymeetingagenda.utils.Mappings;

/**
 * MeetingController
 */
@RequestMapping(path = Mappings.MEETINGS_PATH)
@RestController
public class MeetingController extends CRUDRestControllerImpl<Meeting, MeetingService> {

    public MeetingController(MeetingService service) {
        super(Mappings.MEETINGS_PATH, service);
    }

}
