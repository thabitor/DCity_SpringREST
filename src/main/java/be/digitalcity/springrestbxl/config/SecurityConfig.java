package be.digitalcity.springrestbxl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true, // acces à @PreAuthorize et @PostAuthorize
        securedEnabled = true, // acces à @Secured
        jsr250Enabled = true // acces à @RolesAllowed
)
public class SecurityConfig {

    // LES DROITS:

    // - permitAll : tous les visiteurs (connectés ou pas)
    // - denyAll : personne
    // - authenticated : connecté
    // - anonymous : pas connecté
    // - hasRole : possède le rôle particulier (un rôle est une autorité commencant par ROLE_)
    // - hasAnyRole : possède au moins un des rôles mentionnés
    // - hasAuthority : possède l'authorité particulier
    // - hasAnyAuthorities : possède au moins une des authorités mentionnés

    // - not(): methode avant un droit donnée pour un chemin pour obtenir l'opposé


    // ROLES POSSIBLES:

    // - ADMIN
    // - USER

    // AUTHORITES POSSIBLES:

    // - RECUPERER
    // - MODIFIER

    // LIAISONS:

    // - ADMIN: RECUPERER et MODIFIER et ROLE_ADMIN
    // - USER: RECUPERER et ROLE_USER


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.httpBasic();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/security/test/all").permitAll()
                .antMatchers("/security/test/nobody").denyAll()
                .antMatchers("/reserv/check").permitAll()
                .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .antMatchers("/user/**").permitAll()
                .anyRequest().permitAll();

                // je peux utiliser:
                // - ? : joker pour de 0 à 1 caractère
                // - * : joker pour un segment de 0 à N caractères
                // - **: joker pour de 0 à N segments
                // - {pathVar:regex}: pattern regex pour un segment


        return http.build();

    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
