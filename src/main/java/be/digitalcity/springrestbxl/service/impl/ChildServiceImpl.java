package be.digitalcity.springrestbxl.service.impl;

import be.digitalcity.springrestbxl.exceptions.ElementNotFoundException;
import be.digitalcity.springrestbxl.exceptions.FormValidationException;
import be.digitalcity.springrestbxl.exceptions.InvalidReferenceException;
import be.digitalcity.springrestbxl.mapper.ChildMapper;
import be.digitalcity.springrestbxl.model.dto.ChildDTO;
import be.digitalcity.springrestbxl.model.entities.Child;
import be.digitalcity.springrestbxl.model.entities.Reservation;
import be.digitalcity.springrestbxl.model.entities.Tutor;
import be.digitalcity.springrestbxl.model.forms.ChildInsertForm;
import be.digitalcity.springrestbxl.model.forms.ChildUpdateForm;
import be.digitalcity.springrestbxl.repository.ChildRepository;
import be.digitalcity.springrestbxl.repository.ReservationRepository;
import be.digitalcity.springrestbxl.repository.TutorRepository;
import be.digitalcity.springrestbxl.service.ChildService;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class ChildServiceImpl implements ChildService {

    private final ChildRepository repository;
    private final TutorRepository tutorRepository;
    private final ReservationRepository reservationRepository;
    private final ChildMapper mapper;

    public ChildServiceImpl(ChildRepository repository, ChildMapper mapper, TutorRepository tutorRepository, ReservationRepository reservationRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.tutorRepository = tutorRepository;
        this.reservationRepository = reservationRepository;
    }




    @Override
    public ChildDTO create(ChildInsertForm childToInsert) {
        if(childToInsert == null)
            throw new IllegalArgumentException("child can not be null");
        return mapper.toChildDto( repository.save( mapper.toEntity( childToInsert ) ) );
    }


    @Override
    public ChildDTO update(Long id, ChildUpdateForm childToUpdate) {
        if(childToUpdate == null || id == null)
            throw new IllegalArgumentException("params cannot be null");

        if( !repository.existsById(id) )
            throw new ElementNotFoundException(Child.class, id);

        MultiValueMap<String, String> validationErrors = null;

        if(childToUpdate.getAllergies().stream()
                .anyMatch( (allergy) -> allergy == null || allergy.isBlank() || allergy.isEmpty())) {
            validationErrors = new LinkedMultiValueMap<>();
            validationErrors.add("allergies", "certaines allergies sont invalides");
        }

        Child child = mapper.toEntity(childToUpdate);
        List<Tutor> tutors = tutorRepository.findAllById(childToUpdate.getTutorsIds());

        if( tutors.size() < childToUpdate.getTutorsIds().size() ){
            validationErrors = validationErrors == null ? new LinkedMultiValueMap<>() : validationErrors;
            validationErrors.add("tutors", "certains id ne menent pas à un tuteur");
        }

        if( validationErrors != null )
            throw new FormValidationException(validationErrors);

        child.setTutors( new HashSet<>(tutors) );
        child.setId(id);
        return mapper.toChildDto( repository.save( child ) );
    }

    @Override
    public ChildDTO getOne(Long id) {
        return repository.findById(id)
                .map( mapper::toChildDto )
                .orElseThrow(() -> new ElementNotFoundException(Child.class, id));
    }

    @Override
    public List<ChildDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toChildDto)
                .toList();
    }

    @Override
    @Transactional
    public ChildDTO delete(Long id) {
        Child child = repository.findById(id)
                .orElseThrow( () -> new ElementNotFoundException(Child.class, id));
        repository.delete(child);
        child.setId(null);
        return mapper.toChildDto(child);
    }

    @Override
    public ChildDTO changeTutors(Long id, Collection<Long> idTutors) {
        Child child = repository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(Child.class, id));

        List<Tutor> tutors = tutorRepository.findAllById(idTutors);

        if (tutors.size() < idTutors.size()) {
            List<Long> found = tutors.stream()
                    .map(Tutor::getId)
                    .toList();
            List<Long> notFound = idTutors.stream()
                    .filter(ident -> !found.contains(ident))
                    .toList();

            throw new InvalidReferenceException(notFound);
        }
        child.setTutors(new HashSet<>(tutors));
        return mapper.toChildDto(repository.save(child));
    }

    @Override
    public List<ChildDTO> getAllWithAllergy(String allergy) {
        return repository.findByAllergiesContaining(allergy).stream()
                .map(mapper::toChildDto)
                .toList();
    }

    @Override
    public List<ChildDTO> getAllPresentOnDay(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23,59,59);

        return reservationRepository.findByArriveAfterAndDepartBeforeAndCancelFalse(startOfDay, endOfDay).stream()
                .map(Reservation::getChild )
                .map( mapper::toChildDto )
                .distinct()
                .toList();
    }
}
