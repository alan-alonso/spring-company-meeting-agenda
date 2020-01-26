package br.alan.springcompanymeetingagenda.services;

import org.springframework.stereotype.Service;
import br.alan.springcompanymeetingagenda.domain.Resource;
import br.alan.springcompanymeetingagenda.repositories.ResourceRepository;

/**
 * ResourceService
 */
@Service
public class ResourceService extends CRUDServiceImpl<Resource, ResourceRepository> {

    public ResourceService(ResourceRepository repository) {
        super(repository);
    }
}
