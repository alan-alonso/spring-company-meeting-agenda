package br.alan.springcompanymeetingagenda.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import br.alan.springcompanymeetingagenda.domain.Meeting;

/**
 * MeetingRepository
 */
public interface MeetingRepository extends PagingAndSortingRepository<Meeting, Long> {
}
