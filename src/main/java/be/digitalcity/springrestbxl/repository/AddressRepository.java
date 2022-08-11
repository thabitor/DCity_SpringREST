package be.digitalcity.springrestbxl.repository;

import be.digitalcity.springrestbxl.model.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long>, CustomAddressRepository {
}
