package be.digitalcity.springrestbxl.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@EqualsAndHashCode
public class ChildDTO {

    private long id;
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private String propre;
    private List<String> allergies;
    private List<TutorDTO> tutors;

}
