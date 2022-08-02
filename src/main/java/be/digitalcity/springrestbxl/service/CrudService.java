package be.digitalcity.springrestbxl.service;

import java.util.List;

public interface CrudService <T, TID>{
    // CREATE
    T save(T t);

    // UPDATE

    T update(TID id, T t);

    // READ
    T getOne(TID id);
    List<T> getAll();

    // DELETE
    T delete(TID id);
}
