package es.deusto.proyecto.cine.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
// import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.deusto.proyecto.cine.dto.UsuarioDTO;
import es.deusto.proyecto.cine.model.Rol;
import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.repository.UsuarioRepository;

public class UsuarioServiceTest {
    
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Crear usuario correctamente")
    void crearUsuario() {
        assertNotNull(passwordEncoder);
        assertNotNull(usuarioRepository);

        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setCorreo("juan@correo.es");
        usuario.setNumTelefono("123456789");
        usuario.setContrasenya("contra11");
        usuario.setRol(Rol.USUARIO);

        String contrasenyaEncriptada = usuarioService.encriptar("contra11");
        
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioDTO usuarioDTO = usuarioService.crearUsuario(usuario);

        assertNotNull(usuarioDTO);
        assertEquals(usuarioDTO.getNombre(), "Juan");
        assertEquals(usuarioDTO.getRol(), Rol.USUARIO);
        assertEquals(contrasenyaEncriptada, usuarioDTO.getContrasenya());
        assertTrue(usuario.getCompras() == null);
    }
}
