package be.digitalcity.springrestbxl.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ChildDTO {

    private long id;
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private String propre;
    private List<String> allergies;
    private List<TutorDTO> tutors;

}
