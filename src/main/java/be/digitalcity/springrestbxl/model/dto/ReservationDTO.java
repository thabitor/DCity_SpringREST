package be.digitalcity.springrestbxl.model.dto;

import be.digitalcity.springrestbxl.model.entities.Child;
import be.digitalcity.springrestbxl.model.entities.Tutor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReservationDTO {

    private Long id;

    private LocalDateTime arrive;
    private LocalDateTime depart;

    private ChildDTO child;

    private TutorDTO tutorDepose;
    private TutorDTO tutorReprend;

    @Data
    @Builder
    public static class ChildDTO{
        private Long id;
        private String lastName;
        private String firstName;
        private boolean propre;

        public static ChildDTO toDto(Child entity){
            if(entity == null)
                return null;

            return ChildDTO.builder()
                    .id(entity.getId() )
                    .lastName(entity.getLastName())
                    .firstName(entity.getFirstName())
                    .propre(entity.isPropre())
                    .build();
        }
    }

    @Data
    @Builder
    public static class TutorDTO{
        private Long id;
        private String lastName;
        private String firstName;
        private String numTel;

        public static TutorDTO toDto(Tutor entity){
            if(entity == null)
                return null;

            return TutorDTO.builder()
                    .id(entity.getId())
                    .firstName(entity.getFirstName())
                    .lastName(entity.getLastName())
                    .numTel(entity.getNumTel())
                    .build();
        }
    }

}
