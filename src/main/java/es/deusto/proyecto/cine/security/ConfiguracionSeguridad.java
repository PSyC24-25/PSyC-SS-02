package es.deusto.proyecto.cine.security;

import org.apache.catalina.security.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.HiddenHttpMethodFilter;


@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
            .ignoringRequestMatchers("/usuarios/**")  // Desactiva CSRF solo para el endpoint de usuarios
            )
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/autenticacion/login", "/autenticacion/registro", "/peliculas", "/peliculas/**").permitAll() // Public pages
                .requestMatchers("/usuarios/**").permitAll()
                .requestMatchers("/perfil").authenticated()
                .requestMatchers("/admin/**").authenticated()  // Admin pages
                .requestMatchers(HttpMethod.DELETE, "admin/peliculas/**").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll() // Permitir archivos estáticos
                .anyRequest().authenticated()  // Hay que logearse para el resto
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
                .logoutSuccessUrl("/autenticacion/login")
                .permitAll()
            )
            .sessionManagement(sesion -> sesion
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Ensure session is created if needed
            );
        
        logger.info("SecurityConfig initialized - Admin protected");

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

}
