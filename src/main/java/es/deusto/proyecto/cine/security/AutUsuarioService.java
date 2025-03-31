package es.deusto.proyecto.cine.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.repository.UsuarioRepository;

@Service
public class AutUsuarioService implements UserDetailsService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AutUsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        System.out.println("🔍 Searching for user with email: " + correo);
        Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new UsernameNotFoundException("Usario no encontrado"));

        System.out.println(" Usuario encontrado en BD: " + usuario.getCorreo());
        System.out.println(" Contraseña hasheada en BD: " + usuario.getContrasenya());
        System.out.println(" Coincide con '123456'? " + passwordEncoder.matches("123456", usuario.getContrasenya()));
        return User.withUsername(usuario.getCorreo())
                   .password(usuario.getContrasenya()) 
                   .roles(usuario.getRol().name())
                   .build();
    }
}
