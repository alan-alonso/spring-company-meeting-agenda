package br.alan.springcompanymeetingagenda.web.controllers;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

/**
 * CRUDRestController
 *
 * Basic RESTController interface for CRUD operations with an object.
 * 
 * @author Alan Alonso
 */
public interface CRUDRestController<E> {

    /**
     * Get paged list of all stored objects.
     * 
     * @param pageNumber number of the page for pageable object
     * @param pageSize   size of the page for pageable object
     * @return paged stored objects
     */
    ResponseEntity<Page<E>> listAll(Integer pageNumber, Integer pageSize);

    /**
     * Get stored object by its ID.
     * 
     * @param id existing object ID
     * @return stored object
     * @throws NotFoundException if object with input ID couldn't be found
     */
    ResponseEntity<E> getById(Long id) throws NotFoundException;

    /**
     * Create new object.
     * 
     * @param object to be stored
     * @return path to created object
     * @throws NotFoundException if object with input ID couldn't be found
     */
    ResponseEntity<Object> create(E object);

    /**
     * Modify possibly existing object with the input ID.
     * 
     * @param id     existing object ID
     * @param object modified object
     * @throws NotFoundException
     */
    void update(Long id, E object) throws NotFoundException;

    /**
     * Delete possibly existing object with the input ID.
     * 
     * @param id existing object ID
     * @throws NotFoundException if object with input ID couldn't be found
     */
    void delete(Long id) throws NotFoundException;
}
