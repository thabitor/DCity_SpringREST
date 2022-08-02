package be.digitalcity.springrestbxl.model.forms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@Data
public class ChildInsertForm {
        @NotBlank
        @Size(max = 50)
        private String lastName;
        private String firstName;
        @PastOrPresent
        private LocalDate birthDate;
        private boolean propre;
}
