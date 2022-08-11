package be.digitalcity.springrestbxl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
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
                .antMatchers("/security/test/????").authenticated()
                .antMatchers("/security/test/nobody").denyAll()
                .antMatchers("/security/test/connected").authenticated()
                .antMatchers("/security/test/not-connected").anonymous()
                .antMatchers("/security/test/role/user").hasRole("USER")
                .antMatchers("/security/test/role/admin").hasRole("ADMIN")
                .antMatchers("/security/test/role/any").hasAnyRole("USER", "ADMIN")
                .antMatchers("/security/test/authority/READ").hasAuthority("ROLE_USER")
                .antMatchers("/security/test/authority/any").not().hasAnyAuthority("ROLE_USER", "WRITE")
                .antMatchers("/fake/request/{id::[0-9]+}/**").denyAll()
                // je peux utiliser:
                // - ? : joker pour de 0 à 1 caractère
                // - * : joker pour un segment de 0 à N caractères
                // - **: joker pour de 0 à N segments
                // - {pathVar:regex}: pattern regex pour un segment
                .anyRequest().permitAll();

        return http.build();

    }

}
