package be.digitalcity.springrestbxl.service.impl;

import be.digitalcity.springrestbxl.exceptions.ElementNotFoundException;
import be.digitalcity.springrestbxl.mapper.TutorMapper;
import be.digitalcity.springrestbxl.model.dto.TutorDTO;
import be.digitalcity.springrestbxl.model.entities.Child;
import be.digitalcity.springrestbxl.model.entities.Tutor;
import be.digitalcity.springrestbxl.model.forms.TutorForm;
import be.digitalcity.springrestbxl.repository.ChildRepository;
import be.digitalcity.springrestbxl.repository.TutorRepository;
import be.digitalcity.springrestbxl.service.TutorService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TutorServiceImpl implements TutorService {

    private final TutorRepository repository;
    private final ChildRepository childRepository;
    private final TutorMapper mapper;

    public TutorServiceImpl(TutorRepository repository, TutorMapper mapper, ChildRepository childRepository) {
        this.repository = repository;
        this.childRepository = childRepository;
        this.mapper = mapper;
    }

    @Override
    public TutorDTO create(TutorForm tutorToInsert) {
        if(tutorToInsert == null)
            throw new IllegalArgumentException("tutor can not be null");
        return mapper.toTutorDto(repository.save(mapper.toEntity(tutorToInsert)));
    }


    @Override
    public TutorDTO update(Long id, TutorForm tutorToUpdate) {
        if(tutorToUpdate == null || id == null)
            throw new IllegalArgumentException("params cannot be null");

        if( !repository.existsById(id) )
            throw new ElementNotFoundException(Tutor.class, id);

        Tutor tutor = mapper.toEntity(tutorToUpdate);
        List<Child> children = childRepository.findAllById(tutorToUpdate.getChildrenIds());
        tutor.setChildren(new HashSet<>(children));
        return mapper.toTutorDto(repository.save(tutor));

    }


    @Override
    public TutorDTO getOne(Long id) {
        return repository.findById(id)
                .map( mapper::toTutorDto )
                .orElseThrow(() -> new ElementNotFoundException(Tutor.class, id));
    }

    @Override
    public List<TutorDTO> getAll() {
        return repository.findAll()
                .stream()
                .map( mapper::toTutorDto )
                .toList();
    }

    @Override
    public TutorDTO delete(Long id) {
        Tutor tutor = repository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(Tutor.class, id));
        repository.delete(tutor);
        tutor.setId(null);
        return mapper.toTutorDto(tutor);
    }


    @Override
    public Set<TutorDTO> getAllById(Collection<Long> ids) {
        return null;
    }
}
