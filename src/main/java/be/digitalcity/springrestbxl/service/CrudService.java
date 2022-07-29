package be.digitalcity.springrestbxl.service;

import java.util.List;

public interface CrudService <T, TID>{
    // CREATE / UPDATE
    T save(T t);

    // READ
    T getOne(TID id);
    List<T> getAll();

    // DELETE
    T delete(TID id);
}
