package br.alan.springcompanymeetingagenda.repositories;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import br.alan.springcompanymeetingagenda.domain.Resource;

/**
 * ResourceRepository
 */
public interface ResourceRepository extends PagingAndSortingRepository<Resource, Long> {
    List<Resource> findAllByName(String name);
}
