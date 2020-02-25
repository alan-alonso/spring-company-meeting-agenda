package br.alan.springcompanymeetingagenda.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.alan.springcompanymeetingagenda.domain.Resource;
import br.alan.springcompanymeetingagenda.services.ResourceService;
import br.alan.springcompanymeetingagenda.utils.Mappings;
import io.swagger.annotations.Api;

/**
 * ResourceController
 */
@RequestMapping(path = Mappings.RESOURCES_PATH)
@Api(description = "Resource Endpoints", tags = "Resource")
@RestController
public class ResourceController extends CRUDRestControllerImpl<Resource, ResourceService> {

    public ResourceController(ResourceService service) {
        super(Mappings.RESOURCES_PATH, service);
    }
}
