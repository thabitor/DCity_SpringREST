package be.digitalcity.springrestbxl.repository;

import be.digitalcity.springrestbxl.model.entities.Userr;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserrRepository extends CrudRepository<Userr, Long> {

    Optional<Userr> findByUsername(String username);

}
