package be.digitalcity.springrestbxl.model.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Child extends Person {

    private LocalDate birthDate;
    private boolean propre;

    @ElementCollection
    private List<String> allergies;

    @ManyToMany
    @JoinTable(
            name = "ChildAndTutor",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "tutor_id")
    )
    private List<Tutor> tutors;

    public Child(String firstName, String lastName, LocalDate birthDate, boolean propre) {
        super(firstName, lastName);
        this.birthDate = birthDate;
        this.propre = propre;
    }

    public Child(String firstName, String lastName, LocalDate birthDate, boolean propre, List<String> allergies) {
        super(firstName, lastName);
        this.birthDate = birthDate;
        this.propre = propre;
        this.allergies = allergies;
    }


}