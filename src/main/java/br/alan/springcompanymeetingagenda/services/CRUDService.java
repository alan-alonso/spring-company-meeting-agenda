package br.alan.springcompanymeetingagenda.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * CRUDService
 * 
 * Generic interface with methods for CRUD operations in an entity.
 * 
 * @author Alan Alonso
 */
public interface CRUDService<E> {

    /**
     * Get paged list of all stored objects.
     * 
     * @param pageable pageable request
     * @return paged stored objects
     */
    Page<E> listAll(Pageable pageable);

    /**
     * Get stored object by its ID.
     * 
     * @param id existing object ID
     * @return stored object
     * @throws NotFoundException if object with input ID couldn't be found
     */
    E getById(Long id) throws NotFoundException;

    /**
     * Create new object.
     * 
     * @param object to be stored
     * @return created object
     */
    E create(E object);

    /**
     * Modify possibly existing object with the input ID.
     * 
     * @param id     existing object ID
     * @param object modified object
     * @return modified stored object
     * @throws NotFoundException if object with input ID couldn't be found
     */
    E update(Long id, E object) throws NotFoundException;

    /**
     * Delete possibly existing object with the input ID.
     * 
     * @param id existing object ID
     * @throws NotFoundException if object with input ID couldn't be found
     */
    void delete(Long id) throws NotFoundException;
}
