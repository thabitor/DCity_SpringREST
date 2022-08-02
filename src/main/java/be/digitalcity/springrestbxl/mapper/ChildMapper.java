package be.digitalcity.springrestbxl.mapper;

import be.digitalcity.springrestbxl.model.dto.ChildDTO;
import be.digitalcity.springrestbxl.model.entities.Child;
import be.digitalcity.springrestbxl.model.forms.ChildInsertForm;
import be.digitalcity.springrestbxl.model.forms.ChildUpdateForm;
import org.springframework.stereotype.Component;

@Component
public class ChildMapper {

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

        return entity;

    }


}
