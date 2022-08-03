package be.digitalcity.springrestbxl.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tutor extends Person {

    @Column(nullable = false)
    private String numTel;

    @Column(nullable = false)
    private String address;

    @ManyToMany(mappedBy = "tutors")
    private List<Child> children;

}
