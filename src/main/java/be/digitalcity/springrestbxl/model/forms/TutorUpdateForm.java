package be.digitalcity.springrestbxl.model.forms;

import be.digitalcity.springrestbxl.model.entities.Child;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Data
public class TutorUpdateForm {

    @NotBlank
    private String lastName;
    private String firstName;
    private String address;
    private String numTel;
    private List<Long> childrenIds;
}
