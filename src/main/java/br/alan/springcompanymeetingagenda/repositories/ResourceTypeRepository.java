package br.alan.springcompanymeetingagenda.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import br.alan.springcompanymeetingagenda.domain.ResourceType;

/**
 * ResourceTypeRepository
 */
public interface ResourceTypeRepository extends PagingAndSortingRepository<ResourceType, Long> {
}
