package be.digitalcity.springrestbxl.exceptions;

import java.util.List;

public class InvalidReferenceException extends RuntimeException {

    private final List<? extends Object> notFound;


    public InvalidReferenceException(List<? extends Object> notFound) {
        super( "The following IDs where not found: " + notFound );
        this.notFound = notFound;
    }

    public List<? extends Object> getNotFound() {
        return notFound;
    }
}