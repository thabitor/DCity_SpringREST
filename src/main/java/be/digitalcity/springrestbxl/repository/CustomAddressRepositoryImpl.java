package be.digitalcity.springrestbxl.repository;

import be.digitalcity.springrestbxl.model.entities.Address;
import lombok.NonNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class CustomAddressRepositoryImpl implements CustomAddressRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean exists(@NonNull Address address) {
        String request = "SELECT (COUNT(a) > 0) " +
                " FROM Address a" +
                " WHERE " +
                "   a.city = ?1 AND " +
                "   a.postalCode = ?2 AND " +
                "   a.street = ?3 AND " +
                "   a.number = ?4 AND " +
                "   a.box = ?5 ";
        TypedQuery<Boolean> query = entityManager.createQuery(request, Boolean.class);
        query.setParameter(1, address.getCity() );
        query.setParameter(2, address.getPostalCode() );
        query.setParameter(3, address.getStreet() );
        query.setParameter(4, address.getNumber() );
        query.setParameter(5, address.getBox() );

        return query.getSingleResult();
    }
}
