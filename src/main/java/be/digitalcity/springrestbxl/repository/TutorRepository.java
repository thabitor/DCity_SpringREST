package be.digitalcity.springrestbxl.repository;

import be.digitalcity.springrestbxl.model.entities.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    List<Tutor> findByAddress_CityIsAndChildrenNotNull(String city);

    @Query("SELECT t FROM Tutor t WHERE t.address.city = ?1 AND (t.children.size > 0)")
    List<Tutor> findFromCity(String city);
}
