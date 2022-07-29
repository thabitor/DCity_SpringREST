package be.digitalcity.springrestbxl.mapper;

import be.digitalcity.springrestbxl.model.dto.ChildDTO;
import be.digitalcity.springrestbxl.model.entities.Child;
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


}
