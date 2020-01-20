package br.alan.springcompanymeetingagenda.repositories;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import br.alan.springcompanymeetingagenda.domain.ResourceType;

/**
 * ResourceTypeRepository
 */
@RepositoryRestResource(collectionResourceRel = "resourcetypes", exported = false, path = "resourcetypes")
public interface ResourceTypeRepository extends PagingAndSortingRepository<ResourceType, Long> {
    List<ResourceType> findAllByName(String name);
}
