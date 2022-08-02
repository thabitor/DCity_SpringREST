package be.digitalcity.springrestbxl.mapper;

import be.digitalcity.springrestbxl.model.dto.ChildDTO;
import be.digitalcity.springrestbxl.model.dto.TutorDTO;
import be.digitalcity.springrestbxl.model.entities.Child;
import be.digitalcity.springrestbxl.model.entities.Tutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Component
public class TutorMapper {

    public TutorDTO toTutorDto (Tutor entity) {
        return TutorDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .numTel(entity.getNumTel())
                .address(entity.getAddress())
                .children(entity.getChildren())
                .build();
    }
}
