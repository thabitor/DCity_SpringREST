package be.digitalcity.springrestbxl.service.impl;

import be.digitalcity.springrestbxl.exceptions.ElementNotFoundException;
import be.digitalcity.springrestbxl.exceptions.ReferencedSuppressionException;
import be.digitalcity.springrestbxl.mapper.TutorMapper;
import be.digitalcity.springrestbxl.model.dto.TutorDTO;
import be.digitalcity.springrestbxl.model.entities.Child;
import be.digitalcity.springrestbxl.model.entities.Person;
import be.digitalcity.springrestbxl.model.entities.Tutor;
import be.digitalcity.springrestbxl.model.forms.TutorForm;
import be.digitalcity.springrestbxl.repository.AddressRepository;
import be.digitalcity.springrestbxl.repository.ChildRepository;
import be.digitalcity.springrestbxl.repository.TutorRepository;
import be.digitalcity.springrestbxl.service.TutorService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TutorServiceImpl implements TutorService {

    private final TutorRepository repository;
    private final AddressRepository addressRepository;
    private final TutorMapper mapper;

    public TutorServiceImpl(TutorRepository repository, TutorMapper mapper, AddressRepository addressRepository) {
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.mapper = mapper;
    }

    @Override
    public TutorDTO create(TutorForm tutorToInsert) {
        Tutor tutor = mapper.toEntity(tutorToInsert);

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase();
        addressRepository.findAll( Example.of( tutor.getAddress(), matcher ) ).stream()
                .findFirst()
                .ifPresentOrElse(
                        tutor::setAddress,
                        () -> tutor.setAddress( addressRepository.save(tutor.getAddress()) )
                );

        return mapper.toTutorDto( repository.save( tutor ) );
    }


    @Override
    public TutorDTO update(Long id, TutorForm tutorToUpdate) {
        Tutor tutor = mapper.toEntity(tutorToUpdate);
        tutor.setId(id);
        return mapper.toTutorDto( repository.save(tutor) );
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

        try {
            repository.delete(tutor);
        } catch (DataIntegrityViolationException ex){
            throw new ReferencedSuppressionException(
                    Child.class,
                    tutor.getChildren().stream()
                            .map(Person::getId)
                            .collect(Collectors.toSet()),
                    ex
            );
        }
        tutor.setId(null);
        return mapper.toTutorDto( tutor );
    }


    @Override
    public Set<TutorDTO> getAllById(Collection<Long> ids) {
        return repository.findAllById(ids).stream()
                .map( mapper::toTutorDto )
                .collect(Collectors.toSet());
    }

    @Override
    public List<TutorDTO> getAllFromCityWithChild(String city) {
        return repository.findFromCity(city).stream()
                .map(mapper::toTutorDto)
                .toList();
    }

}
