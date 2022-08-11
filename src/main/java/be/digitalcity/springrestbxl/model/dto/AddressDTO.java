package be.digitalcity.springrestbxl.model.dto;

import be.digitalcity.springrestbxl.model.entities.Address;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {

    private Long number;
    private char box;
    private String street;
    private String city;
    private int postalCode;

    public static AddressDTO fromEntity(Address entity) {
        if(entity == null)
            return null;

        return AddressDTO.builder()
                .number(entity.getId())
                .city(entity.getCity())
                .street(entity.getStreet())
                .box(entity.getBox())
                .postalCode(entity.getPostalCode())
                .build();
    }

}
