package br.alan.springcompanymeetingagenda.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import br.alan.springcompanymeetingagenda.domain.ResourceType;

/**
 * ResourceTypeService
 */
public interface ResourceTypeService {

    /**
     * Create new ResourceType.
     * 
     * @param resourceType
     * @return created ResourceType
     */
    ResourceType createResourceType(ResourceType resourceType);

    /**
     * Modify possibly existing ResourceType.
     * 
     * @param resourceTypeId       existing ResourceType ID
     * @param modifiedResourceType
     * @return modified ResourceType
     * @throws NotFoundException if ResourceType with input ID couldn't be found
     */
    ResourceType updateResourceType(Long resourceTypeId, ResourceType modifiedResourceType)
            throws NotFoundException;

    /**
     * Delete possibly existing ResourceType.
     * 
     * @param resourceTypeId existing ResourceType ID
     * @throws NotFoundException if ResourceType with input ID couldn't be found
     */
    void deleteResourceType(Long resourceTypeId) throws NotFoundException;

}
