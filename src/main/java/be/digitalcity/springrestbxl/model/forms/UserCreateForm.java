package be.digitalcity.springrestbxl.model.forms;

import be.digitalcity.springrestbxl.model.entities.Userr;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class UserCreateForm {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public Userr toEntity(){

        Userr userr = new Userr();
        userr.setUsername(username);
        userr.setPassword(password);
        userr.setRoles(List.of("PERSONNEL"));
        return userr;

    }
}
