package br.alan.springcompanymeetingagenda.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.alan.springcompanymeetingagenda.domain.ResourceType;
import br.alan.springcompanymeetingagenda.repositories.ResourceTypeRepository;
import br.alan.springcompanymeetingagenda.utils.BeanUtilsExtension;
import lombok.RequiredArgsConstructor;

/**
 * ResourceTypeServiceImpl
 */
@RequiredArgsConstructor
@Service
public class ResourceTypeService implements CRUDService<ResourceType> {

    // == fields ==
    private final ResourceTypeRepository resourceTypeRepository;

    // == public methods ==
    @Override
    public Page<ResourceType> listAll(Pageable pageable) {
        return this.resourceTypeRepository.findAll(pageable);
    }

    @Override
    public ResourceType getById(Long id) throws NotFoundException {
        return this.resourceTypeRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public ResourceType create(ResourceType object) {
        return this.resourceTypeRepository.save(object);
    }

    @Override
    public ResourceType update(Long id, ResourceType object) throws NotFoundException {
        // retrieve stored ResourceType
        ResourceType storedResourceType =
                this.resourceTypeRepository.findById(id).orElseThrow(NotFoundException::new);

        // merge the incoming modified resourceType with the stored one while preserving non-null
        // properties from the stored object whose values are null in the incoming.
        BeanUtilsExtension.copyProperties(object, storedResourceType,
                BeanUtilsExtension.getNullPropertyNames(object));

        return this.resourceTypeRepository.save(storedResourceType);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        // retrieve stored resourceType
        ResourceType storedResourceType =
                this.resourceTypeRepository.findById(id).orElseThrow(NotFoundException::new);

        this.resourceTypeRepository.delete(storedResourceType);
    }
}
