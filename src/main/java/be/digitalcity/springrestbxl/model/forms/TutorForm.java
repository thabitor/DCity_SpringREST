package be.digitalcity.springrestbxl.model.forms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;


@Data
public class TutorForm {

    @NotBlank
    private String lastName;
    private String firstName;
    private String address;
    private String numTel;
    private List<Long> childrenIds;
}
