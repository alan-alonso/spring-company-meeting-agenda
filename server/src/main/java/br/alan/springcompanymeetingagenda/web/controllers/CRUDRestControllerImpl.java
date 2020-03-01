package br.alan.springcompanymeetingagenda.web.controllers;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import br.alan.springcompanymeetingagenda.domain.BaseEntity;
import br.alan.springcompanymeetingagenda.services.CRUDService;
import br.alan.springcompanymeetingagenda.web.models.BaseErrorResponse;
import br.alan.springcompanymeetingagenda.web.models.InputDataValidationErrorResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

/**
 * CRUDRestControllerImpl
 * 
 * Basic RESTController implementation for CRUD operations with an object.
 * 
 * @author Alan Alonso
 */
@SuppressWarnings({"deprecation"})
@RequiredArgsConstructor
public abstract class CRUDRestControllerImpl<E extends BaseEntity, S extends CRUDService<E>>
        implements CRUDRestController<E> {

    // == fields ==
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "25";
    private final String apiEndPointPath;
    private final S service;

    // == public methods ==
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Get all", notes = "Get all existing resources", nickname = "listAll")
    @ApiResponses({
            @ApiResponse(code = 403, message = "Forbidden", response = BaseErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found")})
    @Override
    public ResponseEntity<Page<E>> listAll(
            @RequestParam(value = "page", required = false,
                    defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "size", required = false,
                    defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {
        pageNumber = pageNumber < 0 ? Integer.parseInt(DEFAULT_PAGE_NUMBER) : pageNumber;
        pageSize = pageSize < 0 ? Integer.parseInt(DEFAULT_PAGE_SIZE) : pageSize;

        return new ResponseEntity<>(this.service.listAll(PageRequest.of(pageNumber, pageSize)),
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Get by ID", notes = "Get existing resource", nickname = "getById")
    @ApiResponses({
            @ApiResponse(code = 403, message = "Forbidden", response = BaseErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found")})
    @Override
    public ResponseEntity<E> getById(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(this.service.getById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create new", notes = "Create new resource", nickname = "create")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad format for resource",
                    response = InputDataValidationErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = BaseErrorResponse.class)})
    @Override
    public ResponseEntity<Object> create(@RequestBody @Validated E object) {
        E storedObject = this.service.create(object);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", this.apiEndPointPath + '/' + storedObject.getId());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update", notes = "Update existing resource", nickname = "update")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad format for resource",
                    response = InputDataValidationErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = BaseErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void update(@PathVariable Long id, @RequestBody @Validated E object)
            throws NotFoundException {
        this.service.update(id, object);
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete", notes = "Delete existing resource", nickname = "delete")
    @ApiResponses({
            @ApiResponse(code = 403, message = "Forbidden", response = BaseErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable Long id) throws NotFoundException {
        this.service.delete(id);
    }
}
