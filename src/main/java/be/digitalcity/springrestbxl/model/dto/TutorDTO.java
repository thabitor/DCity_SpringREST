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
    private String address;
    private Set<Child> children;


}
