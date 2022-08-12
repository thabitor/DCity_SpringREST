package be.digitalcity.springrestbxl.mapper;

import be.digitalcity.springrestbxl.model.dto.ReservationDTO;
import be.digitalcity.springrestbxl.model.entities.Reservation;
import be.digitalcity.springrestbxl.model.forms.ReservationRequestForm;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper{

    public Reservation toEntity(ReservationRequestForm form){
        if(form == null)
            return null;

        Reservation reservation = new Reservation();

        reservation.setArrive(form.getArrive());
        reservation.setDepart(form.getDepart());

        return reservation;
    }

    public ReservationDTO toDto(Reservation entity){
        if( entity == null )
            return null;

        return ReservationDTO.builder()
                .id(entity.getId())
                .arrive(entity.getArrive())
                .depart(entity.getDepart())
                .child( ReservationDTO.ChildDTO.toDto(entity.getChild()) )
                .tutorDepose( ReservationDTO.TutorDTO.toDto(entity.getTutorDepose()) )
                .tutorReprend( ReservationDTO.TutorDTO.toDto(entity.getTutorReprend()) )
                .build();
    }

}
