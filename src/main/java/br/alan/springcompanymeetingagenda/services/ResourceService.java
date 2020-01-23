package br.alan.springcompanymeetingagenda.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.alan.springcompanymeetingagenda.domain.Resource;
import br.alan.springcompanymeetingagenda.repositories.ResourceRepository;
import br.alan.springcompanymeetingagenda.utils.BeanUtilsExtension;
import lombok.RequiredArgsConstructor;

/**
 * ResourceService
 */
@RequiredArgsConstructor
@Service
public class ResourceService implements CRUDService<Resource> {

    // == fields ==
    private final ResourceRepository resourceRepository;

    // == public methods ==
    @Override
    public Page<Resource> listAll(Pageable pageable) {
        return this.resourceRepository.findAll(pageable);
    }

    @Override
    public Resource getById(Long id) throws NotFoundException {
        return this.resourceRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Resource create(Resource object) {
        return this.resourceRepository.save(object);
    }

    @Override
    public Resource update(Long id, Resource object) throws NotFoundException {
        // retrieve stored Resource
        Resource storedResource =
                this.resourceRepository.findById(id).orElseThrow(NotFoundException::new);

        // merge the incoming modified resource with the stored one while preserving non-null
        // properties from the stored object whose values are null in the incoming.
        BeanUtilsExtension.copyProperties(object, storedResource,
                BeanUtilsExtension.getNullPropertyNames(object));

        return this.resourceRepository.save(storedResource);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        // retrieve stored Resource
        Resource storedResource =
                this.resourceRepository.findById(id).orElseThrow(NotFoundException::new);

        this.resourceRepository.delete(storedResource);
    }

}
