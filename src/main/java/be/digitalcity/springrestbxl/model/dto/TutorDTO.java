package be.digitalcity.springrestbxl.model.dto;

import be.digitalcity.springrestbxl.model.entities.Child;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class TutorDTO {

    private long id;
    private String lastName;
    private String firstName;
    private String numTel;
    private AddressDTO address;
    private List<ChildDTO> children;

    @Data
    @Builder
    public static class ChildDTO {
        private Long id;
        private String lastName;
        private String firstName;
        private LocalDate birthDate;
        private String propre;
        private List<String> allergies;


        public static ChildDTO fromEntity(Child entity){
            if( entity == null )
                return null;

            return ChildDTO.builder()
                    .id( entity.getId() )
                    .firstName( entity.getFirstName() )
                    .lastName( entity.getLastName() )
                    .birthDate( entity.getBirthDate() )
                    .allergies( entity.getAllergies() )
                    .propre( entity.isPropre() ? "propre" : "non-propre" )
                    .build();
        }
    }

}
