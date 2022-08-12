package be.digitalcity.springrestbxl.model.forms;

import be.digitalcity.springrestbxl.validation.constraint.ReservationValid;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@ReservationValid
public class ReservationRequestForm {

    @NotNull
    @Future
    private LocalDateTime arrive;
    @NotNull
    @Future
    private LocalDateTime depart;
    @NotNull
    @Positive
    private Long childId;
    @NotNull
    @Positive
    private Long tutorDepot;
    @NotNull
    @Positive
    private Long tutorDepart;

}
