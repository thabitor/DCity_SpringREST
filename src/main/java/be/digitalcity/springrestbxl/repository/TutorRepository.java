package be.digitalcity.springrestbxl.repository;

import be.digitalcity.springrestbxl.model.entities.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
}
