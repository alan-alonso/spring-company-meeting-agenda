package br.alan.springcompanymeetingagenda.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import br.alan.springcompanymeetingagenda.domain.Meeting;

/**
 * MeetingRepository
 */
@Repository
public interface MeetingRepository extends PagingAndSortingRepository<Meeting, Long> {
}
