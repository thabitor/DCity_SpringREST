package be.digitalcity.springrestbxl.service.impl;

import be.digitalcity.springrestbxl.model.entities.Child;
import be.digitalcity.springrestbxl.repository.ChildRepository;
import be.digitalcity.springrestbxl.service.ChildService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ChildServiceImpl implements ChildService {

    private final ChildRepository repository;

    public ChildServiceImpl(ChildRepository repository) {
        this.repository = repository;
    }

    @Override
    public Child save(Child child) {
        if(child == null)
            throw new IllegalArgumentException("child can not be null");

        return repository.save(child);
    }

    @Override
    public Child update(Long id, Child childToUpdate) {
        if(childToUpdate == null || id == null)
            throw new IllegalArgumentException("params cannot be null");

        if( !repository.existsById(id) )
            throw new EntityNotFoundException();

        childToUpdate.setId(id);

        // TODO gérer les tuteurs

        return repository.save(childToUpdate);    }

    @Override
    public Child getOne(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Child> getAll() {
        return repository.findAll();
    }

    @Override
    public Child delete(Long id) {
        Child child = getOne(id);
        repository.delete(child);
        return child;
    }

    @Override
    public List<Child> getAllById(List<Long> childrenId) {
        return repository.findAllById(childrenId);
    }
}
