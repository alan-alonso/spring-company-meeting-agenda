package br.alan.springcompanymeetingagenda.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import br.alan.springcompanymeetingagenda.domain.BaseEntity;
import br.alan.springcompanymeetingagenda.utils.BeanUtilsExtension;
import lombok.RequiredArgsConstructor;

/**
 * CRUDServiceImpl
 * 
 * A simple implementation of the {@link CRUDService} interface.
 */
@RequiredArgsConstructor
public abstract class CRUDServiceImpl<E extends BaseEntity, R extends PagingAndSortingRepository<E, Long>>
        implements CRUDService<E> {

    // == fields ==
    private final R repository;

    // == public methods ==
    @Transactional
    @Override
    public Page<E> listAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public E getById(Long id) throws NotFoundException {
        return this.repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public E create(E object) {
        return this.repository.save(object);
    }

    @Override
    public E update(Long id, E object) throws NotFoundException {
        // retrieve stored object
        E storedObject = this.repository.findById(id).orElseThrow(NotFoundException::new);

        // merge the incoming modified object with the stored one while preserving non-null
        // properties from the stored object whose values are null in the incoming.
        BeanUtilsExtension.copyProperties(object, storedObject,
                BeanUtilsExtension.getNullPropertyNames(object));

        return this.repository.save(storedObject);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        // retrieve stored Resource
        E storedObject = this.repository.findById(id).orElseThrow(NotFoundException::new);

        this.repository.delete(storedObject);
    }
}
