package br.alan.springcompanymeetingagenda.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
public class ResourceTypeServiceImpl implements ResourceTypeService {

    // == fields ==
    private final ResourceTypeRepository resourceTypeRepository;

    // == public methods ==
    @Override
    public ResourceType createResourceType(ResourceType resourceType) {
        return this.resourceTypeRepository.save(resourceType);
    }

    @Override
    public ResourceType updateResourceType(Long resourceTypeId, ResourceType resourceType)
            throws NotFoundException {
        // retrieve stored ResourceType
        ResourceType storedResourceType = this.resourceTypeRepository.findById(resourceTypeId)
                .orElseThrow(NotFoundException::new);

        // merge the incoming modified resourceType with the stored one while preserving non-null
        // properties from the stored object whose values are null in the incoming.
        BeanUtilsExtension.copyProperties(resourceType, storedResourceType,
                BeanUtilsExtension.getNullPropertyNames(resourceType));

        return this.resourceTypeRepository.save(storedResourceType);
    }

    @Override
    public void deleteResourceType(Long resourceTypeId) throws NotFoundException {
        // retrieve stored resourceType
        ResourceType storedResourceType = this.resourceTypeRepository.findById(resourceTypeId)
                .orElseThrow(NotFoundException::new);

        this.resourceTypeRepository.delete(storedResourceType);
    }
}
