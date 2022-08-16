package be.digitalcity.springrestbxl.controller;

import be.digitalcity.springrestbxl.model.forms.LoginForm;
import be.digitalcity.springrestbxl.model.forms.UserCreateForm;
import be.digitalcity.springrestbxl.service.impl.CustomUserDetailsServiceImpl;
import be.digitalcity.springrestbxl.utils.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserrController {
    private final CustomUserDetailsServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public UserrController(CustomUserDetailsServiceImpl userService, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public void addUser(@Valid @RequestBody UserCreateForm form) {
        userService.create(form);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginForm form){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword()));
        return jwtProvider.createToken(auth);
    }
}
