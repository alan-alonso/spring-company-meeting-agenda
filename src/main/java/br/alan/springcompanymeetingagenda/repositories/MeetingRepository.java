package br.alan.springcompanymeetingagenda.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import br.alan.springcompanymeetingagenda.domain.Meeting;

/**
 * MeetingRepository
 */
@RepositoryRestResource
public interface MeetingRepository extends PagingAndSortingRepository<Meeting, Long> {
}
