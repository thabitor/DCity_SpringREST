package be.digitalcity.springrestbxl.mapper;

import be.digitalcity.springrestbxl.model.dto.AddressDTO;
import be.digitalcity.springrestbxl.model.dto.TutorDTO;
import be.digitalcity.springrestbxl.model.entities.Tutor;
import be.digitalcity.springrestbxl.model.forms.TutorForm;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TutorMapper {

    public TutorDTO toTutorDto (Tutor entity) {
        if(entity == null)
            return null;

        Set<TutorDTO.ChildDTO> children = new HashSet<>();

        if( entity.getChildren() != null )
            entity.getChildren().stream()
                    .map( TutorDTO.ChildDTO::fromEntity )
                    .forEach(children::add);

        return TutorDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .numTel(entity.getNumTel())
                .address( AddressDTO.fromEntity(entity.getAddress()) )
                .children( children.stream().toList() )
                .build();
    }

    public Tutor toEntity(TutorForm form){

        if( form == null )
            return null;

        Tutor tutor = new Tutor();

        tutor.setFirstName( form.getFirstName() );
        tutor.setLastName( form.getLastName() );
        tutor.setAddress( form.getAddress().toEntity() );
        tutor.setNumTel( form.getNumTel() );

        return tutor;

    }

}
