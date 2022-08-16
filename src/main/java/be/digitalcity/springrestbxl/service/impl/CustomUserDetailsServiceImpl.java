package be.digitalcity.springrestbxl.service.impl;

import be.digitalcity.springrestbxl.model.entities.Userr;
import be.digitalcity.springrestbxl.model.forms.UserCreateForm;
import be.digitalcity.springrestbxl.repository.UserrRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserrRepository repository;
    private final PasswordEncoder encoder;

    public CustomUserDetailsServiceImpl(UserrRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("connexion impossible"));
    }

    public void create(UserCreateForm toCreate){
        Userr userr = toCreate.toEntity();
        userr.setPassword( encoder.encode(userr.getPassword()) );
        repository.save( userr );
    }

}