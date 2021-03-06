package br.alan.springcompanymeetingagenda.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.alan.springcompanymeetingagenda.domain.ResourceType;
import br.alan.springcompanymeetingagenda.services.ResourceTypeService;
import br.alan.springcompanymeetingagenda.utils.Mappings;
import io.swagger.annotations.Api;



/**
 * ResourceTypeController
 */
@RequestMapping(path = Mappings.RESOURCE_TYPES_PATH)
@Api(description = "Resource Type Endpoints", tags = "Resource Type")
@RestController
public class ResourceTypeController
        extends CRUDRestControllerImpl<ResourceType, ResourceTypeService> {

    public ResourceTypeController(ResourceTypeService service) {
        super(Mappings.RESOURCE_TYPES_PATH, service);

    }
}
