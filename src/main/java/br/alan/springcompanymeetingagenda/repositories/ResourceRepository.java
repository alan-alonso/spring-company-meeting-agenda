package br.alan.springcompanymeetingagenda.repositories;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import br.alan.springcompanymeetingagenda.domain.Resource;

/**
 * ResourceRepository
 */
@RepositoryRestResource(exported = false)
public interface ResourceRepository extends PagingAndSortingRepository<Resource, Long> {
    List<Resource> findAllByName(String name);
}
