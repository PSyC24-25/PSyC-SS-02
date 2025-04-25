package es.deusto.proyecto.cine.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import jakarta.persistence.EntityNotFoundException;

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

    @Test
    @DisplayName("Actualizar usuario correctamente")
    void actualizarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setCodUsuario(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setCorreo("juan@correo.es");
        usuario.setNumTelefono("123456789");
        usuario.setRol(Rol.USUARIO);

        UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "Juan Updated", "Pérez Updated", "juan@correo.es", "987654321", "contra11", Rol.ADMIN);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioDTO updatedUsuarioDTO = usuarioService.actualizarUsuario(1L, usuarioDTO);

        assertNotNull(updatedUsuarioDTO);
        assertEquals("Juan Updated", updatedUsuarioDTO.getNombre());
        assertEquals("Pérez Updated", updatedUsuarioDTO.getApellido());
        assertEquals("987654321", updatedUsuarioDTO.getNumTelefono());
        assertEquals(Rol.ADMIN, updatedUsuarioDTO.getRol());
    }

    @Test
    @DisplayName("Borrar usuario correctamente")
    void borrarUsuario() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> usuarioService.borrarUsuario(1L));

        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Obtener usuario por ID correctamente")
    void getUsuarioById() {
        Usuario usuario = new Usuario();
        usuario.setCodUsuario(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setCorreo("juan@correo.es");
        usuario.setNumTelefono("123456789");
        usuario.setRol(Rol.USUARIO);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioDTO usuarioDTO = usuarioService.getUsuarioById(1L);

        assertNotNull(usuarioDTO);
        assertEquals("Juan", usuarioDTO.getNombre());
        assertEquals(Rol.USUARIO, usuarioDTO.getRol());
    }


    @Test
    @DisplayName("Obtener todos los usuarios correctamente")
    void getAllUsuarios() {
        Usuario usuario1 = new Usuario();
        usuario1.setCodUsuario(1L);
        usuario1.setNombre("Juan");

        Usuario usuario2 = new Usuario();
        usuario2.setCodUsuario(2L);
        usuario2.setNombre("Maria");

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        List<UsuarioDTO> usuarios = usuarioService.getAllUsuarios();

        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
        assertEquals("Juan", usuarios.get(0).getNombre());
        assertEquals("Maria", usuarios.get(1).getNombre());
    }

    @Test
    @DisplayName("Verificar contraseña correctamente")
    void verificarContraseña() {
        when(passwordEncoder.matches("rawPassword", "encodedPassword")).thenReturn(true);

        boolean isMatch = usuarioService.verificarContraseña("rawPassword", "encodedPassword");

        assertTrue(isMatch);
    }

    @Test
    @DisplayName("Convertir a entidad - Usuario no encontrado")
    void convertirAEntidadUsuarioNoEncontrado() {
        UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "Juan", "Pérez", "juan@correo.es", "123456789", "contra11", Rol.USUARIO);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.actualizarUsuario(1L, usuarioDTO);
        });

        assertEquals("No se ha encontrado compra con ID " + 1L, exception.getMessage());
    }

    @Test
    @DisplayName("Crear usuario - Correo ya registrado")
    void crearUsuarioCorreoYaRegistrado() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("juan@correo.es");

        when(usuarioRepository.findByCorreo("juan@correo.es")).thenReturn(Optional.of(usuario));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.crearUsuario(usuario);
        });

        assertEquals("El correo electrónico ya está registrado", exception.getMessage());
    }

    @Test
    @DisplayName("Crear usuario - ID no debe estar presente")
    void crearUsuarioIdNoDebeEstarPresente() {
        Usuario usuario = new Usuario();
        usuario.setCodUsuario(1L);
        usuario.setCorreo("juan@correo.es");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.crearUsuario(usuario);
        });

        assertEquals("El ID del usuario no debe estar presente al crear un nuevo usuario", exception.getMessage());
    }

    @Test
    @DisplayName("Obtener usuario por email - Usuario encontrado")
    void obtenerUsuarioPorEmailUsuarioEncontrado() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("juan@correo.es");

        when(usuarioRepository.findByCorreo("juan@correo.es")).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.obtenerUsuarioPorEmail("juan@correo.es");

        assertNotNull(result);
        assertEquals("juan@correo.es", result.getCorreo());
    }

    @Test
    @DisplayName("Obtener usuario por email - Usuario no encontrado")
    void obtenerUsuarioPorEmailUsuarioNoEncontrado() {
        when(usuarioRepository.findByCorreo("juan@correo.es")).thenReturn(Optional.empty());

        Usuario result = usuarioService.obtenerUsuarioPorEmail("juan@correo.es");

        assertNull(result);
    }

    @Test
    @DisplayName("Actualizar usuario - Usuario no encontrado")
    void actualizarUsuarioUsuarioNoEncontrado() {
        UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "Juan", "Pérez", "juan@correo.es", "123456789", "contra11", Rol.USUARIO);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            usuarioService.actualizarUsuario(1L, usuarioDTO);
        });

        assertEquals("No se ha encontrado compra con ID 1", exception.getMessage());
    }

    @Test
    @DisplayName("Borrar usuario - Usuario no encontrado")
    void borrarUsuarioUsuarioNoEncontrado() {
        when(usuarioRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.borrarUsuario(1L);
        });

        assertEquals("No existe usuario con id: 1", exception.getMessage());
    }
}
