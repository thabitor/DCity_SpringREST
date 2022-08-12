package be.digitalcity.springrestbxl.model.forms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReservationCancellationForm {

    @NotNull
    private Long id;
    @NotBlank
    private String motif;

}
