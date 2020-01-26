package br.alan.springcompanymeetingagenda.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.alan.springcompanymeetingagenda.domain.Resource;
import br.alan.springcompanymeetingagenda.services.ResourceService;
import br.alan.springcompanymeetingagenda.utils.Mappings;

/**
 * ResourceController
 */
@RequestMapping(path = Mappings.RESOURCES_PATH)
@RestController
public class ResourceController extends CRUDRestControllerImpl<Resource, ResourceService> {

    public ResourceController(ResourceService service) {
        super(Mappings.RESOURCES_PATH, service);
    }
}
