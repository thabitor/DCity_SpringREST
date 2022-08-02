package be.digitalcity.springrestbxl.model.forms;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
@Data
public class ChildUpdateForm {
        private String lastName;
        private String firstName;
        private LocalDate birthDate;
        private boolean propre;
        private List<String> allergies;
        private Set<Long> TutorsId;
}
