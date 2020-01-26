package br.alan.springcompanymeetingagenda.services;

import org.springframework.stereotype.Service;
import br.alan.springcompanymeetingagenda.domain.ResourceType;
import br.alan.springcompanymeetingagenda.repositories.ResourceTypeRepository;

/**
 * ResourceTypeServiceImpl
 */
@Service
public class ResourceTypeService extends CRUDServiceImpl<ResourceType, ResourceTypeRepository> {

    public ResourceTypeService(ResourceTypeRepository repository) {
        super(repository);
    }
}
