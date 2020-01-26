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
public abstract class CRUDServiceTest<E> {

    // == fields ==
    // repository mock
    private PagingAndSortingRepository<E, Long> repository;
    // service to be tested
    private CRUDService<E> service;

    /**
     * {@link CRUDService#listAll()} test method.
     */
    protected void listAllTest() {
        // arrange
        Page<E> pagedObj = Mockito.mock(Page.class);

        when(this.repository.findAll(any(Pageable.class))).thenReturn(pagedObj);

        // act
        Page<E> storedObject = this.service.listAll(PageRequest.of(0, 1));

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
    protected void getByIdTest(E returnObject) throws NotFoundException {
        // arrange
        when(this.repository.findById(anyLong())).thenReturn(Optional.of(returnObject));

        // act
        E storedObject = this.service.getById(1L);

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
    protected void createTest(E returnObject) {
        // arrange
        when(this.repository.save(any())).thenReturn(returnObject);

        // act
        E storedObj = this.service.create(returnObject);

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
    protected void updateTest(E modifiedObject, E storedObject, E returnObject)
            throws NotFoundException {
        // arrange
        when(this.repository.findById(anyLong())).thenReturn(Optional.of(storedObject));
        when(this.repository.save(any())).thenReturn(returnObject);

        // act
        E resultObject = this.service.update(1L, modifiedObject);

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
    protected void updateShouldThrow(E modifiedObject) throws NotFoundException {
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
    protected void deleteTest(E returnObject) throws NotFoundException {
        // arrange
        when(this.repository.findById(anyLong())).thenReturn(Optional.of(returnObject));
        Mockito.doNothing().when(this.repository).delete(any());

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
