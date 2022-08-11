package be.digitalcity.springrestbxl.model.entities;

import be.digitalcity.springrestbxl.model.dto.TutorDTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    private Long reservationId;
    private LocalDate dateIn;
    @OneToOne
    private Child child;
    @OneToOne
    private Tutor tutor;

}
