package be.digitalcity.springrestbxl.mapper;

import be.digitalcity.springrestbxl.model.dto.ChildDTO;
import be.digitalcity.springrestbxl.model.dto.TutorDTO;
import be.digitalcity.springrestbxl.model.entities.Child;
import be.digitalcity.springrestbxl.model.forms.ChildInsertForm;
import be.digitalcity.springrestbxl.model.forms.ChildUpdateForm;
import be.digitalcity.springrestbxl.service.TutorService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ChildMapper {

    private final TutorMapper tutorMapper;

    public ChildMapper(TutorMapper tutorMapper) {
        this.tutorMapper = tutorMapper;
    }

    public ChildDTO toChildDto (Child entity) {

        if( entity == null )
            return null;

        Set<TutorDTO> dtos = entity.getTutors().stream()
                .map( tutorMapper::toTutorDto )
                .collect(Collectors.toSet());

        return ChildDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .birthDate(entity.getBirthDate())
                .allergies(entity.getAllergies())
                .propre(entity.isPropre() ? "Yes" : "No")
                .tutors( dtos.stream().toList() )
                .build();
    }
    public Child toEntity(ChildInsertForm form){

        if( form == null )
            return null;

        Child childInserted = new Child();

        childInserted.setFirstName(form.getFirstName());
        childInserted.setLastName(form.getLastName());
        childInserted.setBirthDate(form.getBirthDate());
        childInserted.setPropre(form.isPropre());
        return childInserted;
    }


    // Il est a remarquer qu'on ne mappe pas l'id ou les tuteurs
    public Child toEntity(ChildUpdateForm form){

        if( form == null )
            throw new IllegalArgumentException();
        Child childEntityUpdated = new Child();

        childEntityUpdated.setFirstName(form.getFirstName());
        childEntityUpdated.setLastName(form.getLastName());
        childEntityUpdated.setBirthDate(form.getBirthDate());
        childEntityUpdated.setPropre(form.isPropre());
        childEntityUpdated.setAllergies(form.getAllergies());

        return childEntityUpdated;

    }

}
