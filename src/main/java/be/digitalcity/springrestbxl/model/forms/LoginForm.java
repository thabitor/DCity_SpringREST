package be.digitalcity.springrestbxl.model.forms;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}