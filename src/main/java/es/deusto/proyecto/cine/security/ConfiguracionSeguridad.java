package es.deusto.proyecto.cine.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/autenticacion/login", "/autenticacion/registro", "/peliculas").permitAll()  // Public pages
                .requestMatchers("/admin/**").hasRole("ADMIN")  // Admin pages
                .anyRequest().authenticated()  // Everything else requires login
            )
            .formLogin(form -> form
                .loginPage("/autenticacion/login")
                .usernameParameter("correo")
                .passwordParameter("contrasenya")
                .defaultSuccessUrl("/", true)
                .failureUrl("/autenticacion/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
