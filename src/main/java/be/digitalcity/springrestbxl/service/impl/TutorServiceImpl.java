package be.digitalcity.springrestbxl.service.impl;

import be.digitalcity.springrestbxl.model.entities.Child;
import be.digitalcity.springrestbxl.model.entities.Tutor;
import be.digitalcity.springrestbxl.repository.TutorRepository;
import be.digitalcity.springrestbxl.service.TutorService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
@Service
public class TutorServiceImpl implements TutorService {

    private final TutorRepository repository;

    public TutorServiceImpl(TutorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tutor save(Tutor tutor) {
        if(tutor == null)
            throw new IllegalArgumentException("tutor can not be null");

        return repository.save(tutor);
    }

    @Override
    public Tutor update(Long id, Tutor tutorToUpdate) {
        if(tutorToUpdate == null || id == null)
            throw new IllegalArgumentException("params cannot be null");

        if( !repository.existsById(id) )
            throw new EntityNotFoundException();

        tutorToUpdate.setId(id);

        // TODO gérer les tuteurs

        return repository.save(tutorToUpdate);
    }

    @Override
    public Tutor getOne(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Tutor> getAll() {
        return repository.findAll();
    }

    @Override
    public Tutor delete(Long id) {
        Tutor tutor = getOne(id);
        repository.delete(tutor);
        return tutor;
    }
}
