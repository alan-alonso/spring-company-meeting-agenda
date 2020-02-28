package br.alan.springcompanymeetingagenda.repositories;

import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import br.alan.springcompanymeetingagenda.domain.Role;

/**
 * RoleRepository
 */
@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
