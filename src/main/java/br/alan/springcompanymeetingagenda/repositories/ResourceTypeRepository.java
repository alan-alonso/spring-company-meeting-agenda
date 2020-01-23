package br.alan.springcompanymeetingagenda.repositories;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import br.alan.springcompanymeetingagenda.domain.ResourceType;

/**
 * ResourceTypeRepository
 */
public interface ResourceTypeRepository extends PagingAndSortingRepository<ResourceType, Long> {
    List<ResourceType> findAllByName(String name);
}
