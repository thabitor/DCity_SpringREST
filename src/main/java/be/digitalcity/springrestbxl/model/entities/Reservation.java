package be.digitalcity.springrestbxl.model.entities;

import be.digitalcity.springrestbxl.model.dto.TutorDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime arrive;
    @Column(nullable = false)
    private LocalDateTime depart;

    private boolean cancel = false;
    private String motifCancellation;

    @ManyToOne(optional = false)
    private Tutor tutorDepose;
    @ManyToOne(optional = false)
    private Tutor tutorReprend;
    @ManyToOne(optional = false)
    private Child child;

}
