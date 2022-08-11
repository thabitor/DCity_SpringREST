package be.digitalcity.springrestbxl.model.forms;

import be.digitalcity.springrestbxl.model.entities.Address;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class AddressForm {

    @Positive
    private int number;
    @Min(97) @Max(122)
    private char box;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @Positive
    private int postalCode;

    public Address toEntity(){

        Address entity = new Address();

        entity.setCity(this.city);
        entity.setStreet(this.street);
        entity.setBox(this.box);
        entity.setPostalCode(this.postalCode);
        entity.setNumber(this.number);

        return entity;

    }
}
