package be.digitalcity.springrestbxl.repository;

import be.digitalcity.springrestbxl.model.entities.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ChildRepository extends JpaRepository<Child, Long> {
}
