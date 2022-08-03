package be.digitalcity.springrestbxl.mapper;

import be.digitalcity.springrestbxl.model.dto.TutorDTO;
import be.digitalcity.springrestbxl.model.entities.Tutor;
import be.digitalcity.springrestbxl.model.forms.TutorInsertForm;
import be.digitalcity.springrestbxl.model.forms.TutorUpdateForm;
import be.digitalcity.springrestbxl.service.ChildService;
import org.springframework.stereotype.Component;

@Component
public class TutorMapper {

//    private final ChildService childService;
//    public TutorMapper(ChildService childService) {
//        this.childService = childService;
//    }

    public TutorDTO toTutorDto (Tutor entity) {
        return TutorDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .numTel(entity.getNumTel())
                .address(entity.getAddress())
//                .children(entity.getChildren())
                .build();
    }

    public Tutor toEntity(TutorInsertForm form){

        if( form == null )
            return null;

        Tutor tutor = new Tutor();

        tutor.setFirstName( form.getFirstName() );
        tutor.setLastName( form.getLastName() );
        tutor.setAddress( form.getAddress() );
        tutor.setNumTel( form.getNumTel() );

        return tutor;

    }

    public Tutor toEntity(TutorUpdateForm form){

        Tutor entity = new Tutor();

        entity.setFirstName(form.getFirstName());
        entity.setLastName(form.getLastName());
        entity.setAddress( form.getAddress() );
        entity.setNumTel( form.getNumTel() );
//        entity.setChildren( childService.getAllById(form.getChildrenIds()) );

        return entity;

    }
}
