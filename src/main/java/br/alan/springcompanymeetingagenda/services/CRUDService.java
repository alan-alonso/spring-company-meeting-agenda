package br.alan.springcompanymeetingagenda.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * CRUDService
 */
public interface CRUDService<T> {
    
    /**
     * Get paged list of all stored objects.
     * 
     * @param pageable pageable request
     * @return paged stored resources
     */
    Page<T> listAll(Pageable pageable);

    /**
     * Get stored object by its ID.
     * 
     * @param id existing object ID
     * @return stored object
     * @throws NotFoundException if object with input ID couldn't be found
     */
    T getById(Long id) throws NotFoundException;

    /**
     * Create new object.
     * 
     * @param T
     * @return created object
     */
    T create(T object);

    /**
     * Modify possibly existing object with the input ID.
     * 
     * @param id     existing object ID
     * @param object modified object
     * @return modified stored object
     * @throws NotFoundException if object with input ID couldn't be found
     */
    T update(Long id, T object) throws NotFoundException;

    /**
     * Delete possibly existing object with the input ID.
     * 
     * @param id existing object ID
     * @throws NotFoundException if object with input ID couldn't be found
     */
    void delete(Long id) throws NotFoundException;
}