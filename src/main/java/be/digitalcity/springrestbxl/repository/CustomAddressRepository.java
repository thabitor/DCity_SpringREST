package be.digitalcity.springrestbxl.repository;

import be.digitalcity.springrestbxl.model.entities.Address;

public interface CustomAddressRepository {

    boolean exists(Address address);
}
