package be.digitalcity.springrestbxl.service;

import java.util.List;

public interface CrudService <T, TID, TIFORM, TUFORM>{
    // CREATE
    T create(TIFORM toInsert);

    // UPDATE

    T update(TID id, TUFORM toUpdate);

    // READ
    T getOne(TID id);
    List<T> getAll();

    // DELETE
    T delete(TID id);

}
