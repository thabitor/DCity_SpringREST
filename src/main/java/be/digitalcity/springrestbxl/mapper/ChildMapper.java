package be.digitalcity.springrestbxl.mapper;

import be.digitalcity.springrestbxl.model.dto.ChildDTO;
import be.digitalcity.springrestbxl.model.entities.Child;
import be.digitalcity.springrestbxl.model.forms.ChildInsertForm;
import be.digitalcity.springrestbxl.model.forms.ChildUpdateForm;
import be.digitalcity.springrestbxl.service.TutorService;
import org.springframework.stereotype.Component;

@Component
public class ChildMapper {

    private final TutorService tutorService;

    public ChildMapper(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    public ChildDTO toChildDto (Child entity) {
        return ChildDTO.builder()
                        .id(entity.getId())
                                .firstName(entity.getFirstName())
                                        .lastName(entity.getLastName())
                                                .birthDate(entity.getBirthDate())
                                                        .allergies(entity.getAllergies())
                                                                .propre(entity.isPropre() ? "Yes" : "No")
                .build();
    }
    public Child toEntity(ChildInsertForm form){

        if( form == null )
            return null;

        Child child = new Child();

        child.setFirstName( form.getFirstName() );
        child.setLastName( form.getLastName() );
        child.setBirthDate( form.getBirthDate() );
        child.setPropre( form.isPropre() );
        child.setTutors( tutorService.getAllById(form.getTutorsIds()) );

        return child;

    }

    // Il est a remarquer qu'on ne mappe pas l'id ou les tuteurs
    public Child toEntity(ChildUpdateForm form){

        Child entity = new Child();

        entity.setFirstName(form.getFirstName());
        entity.setLastName(form.getLastName());
        entity.setBirthDate(form.getBirthDate());
        entity.setPropre(form.isPropre());
        entity.setAllergies(form.getAllergies());
        entity.setTutors( tutorService.getAllById(form.getTutorsIds()) );

        return entity;

    }


}
