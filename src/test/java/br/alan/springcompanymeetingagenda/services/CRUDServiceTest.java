package br.alan.springcompanymeetingagenda.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.mockito.Mockito;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import lombok.Setter;

/**
 * CRUDServiceTest
 * 
 * Provides generic test methods for simple {@link CRUDService} implementations.
 */
@Setter
@SuppressWarnings({"unchecked"})
public abstract class CRUDServiceTest<T> {

    // == fields ==
    // repository mock
    private PagingAndSortingRepository<T, Long> repository;
    // service to be tested
    private CRUDService<T> service;

    /**
     * {@link CRUDService#listAll()} test method.
     */
    protected void listAllTest() {
        // arrange
        Page<T> pagedObj = Mockito.mock(Page.class);

        when(this.repository.findAll(any(Pageable.class))).thenReturn(pagedObj);

        // act
        Page<T> storedObject = this.service.listAll(PageRequest.of(0, 1));

        // assert
        verify(this.repository).findAll(any(Pageable.class));
        assertEquals(pagedObj, storedObject);
    }

    /**
     * {@link CRUDService#getById()} test method.
     * 
     * @param returnObject expected return object
     * @throws NotFoundException if object with input ID couldn't be found
     */
    protected void getByIdTest(T returnObject) throws NotFoundException {
        // arrange
        when(this.repository.findById(anyLong())).thenReturn(Optional.of(returnObject));

        // act
        T storedObject = this.service.getById(1L);

        // assert
        verify(this.repository).findById(anyLong());
        assertEquals(returnObject, storedObject);
    }

    /**
     * {@link CRUDService#getById()} test method should throw {@link NotFoundException} as input ID
     * doesn't exist.
     * 
     * @throws NotFoundException if object with input ID couldn't be found
     */
    protected void getByIdShouldThrow() throws NotFoundException {
        // arrange
        when(this.repository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        // act / assert
        assertThrows(NotFoundException.class, () -> this.service.getById(1L));
    }

    /**
     * {@link CRUDService#create()} test method.
     * 
     * @param returnObject
     */
    protected void createTest(T returnObject) {
        // arrange
        when(this.repository.save(any())).thenReturn(returnObject);

        // act
        T storedObj = this.service.create(returnObject);

        // assert
        verify(this.repository).save(returnObject);
        assertEquals(returnObject, storedObj);
    }

    /**
     * {@link CRUDService#update()} test method.
     * 
     * @param modifiedObject object with modified properties
     * @param storedObject   return object for findById repository method
     * @param returnObject   expected return object
     * @throws NotFoundException if object with input ID couldn't be found
     */
    protected void updateTest(T modifiedObject, T storedObject, T returnObject)
            throws NotFoundException {
        // arrange
        when(this.repository.findById(anyLong())).thenReturn(Optional.of(storedObject));
        when(this.repository.save(any())).thenReturn(returnObject);

        // act
        T resultObject = this.service.update(1L, modifiedObject);

        // assert
        verify(this.repository).findById(anyLong());
        verify(this.repository).save(returnObject);
        assertEquals(returnObject, resultObject);
    }

    /**
     * {@link CRUDService#update()} test method should throw {@link NotFoundException} as input ID
     * doesn't exist.
     * 
     * @param modifiedObject object with modified properties
     * @throws NotFoundException if object with input ID couldn't be found
     */
    protected void updateShouldThrow(T modifiedObject) throws NotFoundException {
        // arrange
        when(this.repository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        // act / assert
        assertThrows(NotFoundException.class, () -> this.service.update(1L, modifiedObject));
    }

    /**
     * {@link CRUDService#delete()} test method.
     * 
     * @param returnObject expected return object
     * @throws NotFoundException if object with input ID couldn't be found
     */
    protected void deleteTest(T returnObject) throws NotFoundException {
        // arrange
        when(this.repository.findById(anyLong())).thenReturn(Optional.of(returnObject));

        // act
        this.service.delete(1L);

        // assert
        verify(this.repository).delete(returnObject);
    }

    /**
     * {@link CRUDService#delete()} test method should throw {@link NotFoundException} as input ID
     * doesn't exist.
     * 
     * @throws NotFoundException if object with input ID couldn't be found
     */
    protected void deleteShouldThrow() throws NotFoundException {
        // arrange
        when(this.repository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        // act / assert
        assertThrows(NotFoundException.class, () -> this.service.delete(1L));
    }
}
