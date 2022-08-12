package be.digitalcity.springrestbxl.service;

import be.digitalcity.springrestbxl.model.dto.ReservationDTO;
import be.digitalcity.springrestbxl.model.forms.ReservationCancellationForm;
import be.digitalcity.springrestbxl.model.forms.ReservationRequestForm;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {

    ReservationDTO requestReservation(ReservationRequestForm form);
    boolean dateAvailable(LocalDateTime arrive, LocalDateTime depart);
    void cancelReservation(@Valid ReservationCancellationForm form);
    List<ReservationDTO> futureReservOfChild(Long childId);
    List<ReservationDTO> futureOfCurrentMonth();

}
