package be.digitalcity.springrestbxl.model.forms;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@Data
public class TutorForm {

    @NotBlank
    @Size(min = 2,max = 255)
    private String lastName;
    @NotBlank
    @Size(min = 2,max = 255)
    private String firstName;
    @Valid
    @NotNull
    private AddressForm address;
    @NotNull
    @Pattern(regexp = "^(((\\+|00)32[ ]?(?:\\(0\\)[ ]?)?)|0){1}(4(60|[789]\\d)\\/?(\\s?\\d{2}\\.?){2}(\\s?\\d{2})|(\\d\\/?\\s?\\d{3}|\\d{2}\\/?\\s?\\d{2})(\\.?\\s?\\d{2}){2})$")
    private String numTel;
}
