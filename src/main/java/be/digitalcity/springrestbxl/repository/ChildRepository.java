package be.digitalcity.springrestbxl.repository;

import be.digitalcity.springrestbxl.model.entities.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChildRepository extends JpaRepository<Child, Long> {

    List<Child> findByAllergiesContaining(String allergy);

    List<Child> findByFirstNameAndLastName(String firstName, String lastName);
}
