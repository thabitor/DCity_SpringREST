package be.digitalcity.springrestbxl.config;

import be.digitalcity.springrestbxl.filters.JwtAuthFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter authFilter) throws Exception {
        // http.httpBasic(); // we replace BasicAuth for Auth0
        http.csrf().disable(); // we disable this to avoid csrf errors
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy((SessionCreationPolicy.STATELESS));
        http.authorizeRequests()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/**").permitAll()
                .antMatchers("/security/test/all").permitAll()
                .antMatchers("/security/test/nobody").denyAll()
                .antMatchers("/security/test/connected").authenticated()
                .antMatchers("/security/test/not-connected").anonymous()
                .antMatchers("/security/test/role/user").hasRole("USER")
                .antMatchers("/security/test/role/admin").hasRole("ADMIN")
                .antMatchers("/security/test/role/any").hasAnyRole("USER", "ADMIN")
                .antMatchers("/security/test/authority/read").hasAuthority("ROLE_USER")
                .antMatchers("/security/test/authority/any").hasAnyAuthority("ROLE_USER", "READ", "WRITE")
                .antMatchers("/reservations/check-date").permitAll()
                .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                .antMatchers("/user/**").permitAll()
                .anyRequest().authenticated();

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
