package be.digitalcity.springrestbxl.exceptions;

import java.time.LocalDateTime;

public class UnavailableDatesException extends RuntimeException {

    private final LocalDateTime arrive;
    private final LocalDateTime depart;

    public UnavailableDatesException(LocalDateTime arrive, LocalDateTime depart) {
        super("Les dates entrées ne sont pas disponibles");
        this.arrive = arrive;
        this.depart = depart;
    }

    public LocalDateTime getArrive() {
        return arrive;
    }

    public LocalDateTime getDepart() {
        return depart;
    }
}
