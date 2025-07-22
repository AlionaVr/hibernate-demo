package org.hibernatedemo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(
        securedEnabled = true,       // включает @Secured
        jsr250Enabled = true,        // включает @RolesAllowed
        prePostEnabled = true)      // включает @PreAuthorize / @PostAuthorize
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails reader = User.withUsername("reader")
                .password(encoder.encode("password"))
                .roles("READ")
                .build();

        UserDetails writer = User.withUsername("writer")
                .password(encoder.encode("password"))
                .roles("WRITE")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(encoder.encode("password"))
                .roles("READ", "WRITE", "DELETE")
                .build();
        return new InMemoryUserDetailsManager(reader, writer, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/persons").permitAll()
                        .requestMatchers("/persons/by-age",
                                "/persons/by-name-surname",
                                "/persons/by-city").hasRole("READ")
                        .requestMatchers("/persons/add",
                                "/persons/update/**").hasRole("WRITE")
                        .requestMatchers("/persons/delete/**").hasRole("DELETE")
                        .anyRequest().authenticated())
                .httpBasic(httpBasic -> httpBasic.realmName("Persons API"))
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll
                )
                .logout(LogoutConfigurer::permitAll)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}