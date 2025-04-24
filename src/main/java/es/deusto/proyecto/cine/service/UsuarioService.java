package es.deusto.proyecto.cine.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.deusto.proyecto.cine.dto.UsuarioDTO;
import es.deusto.proyecto.cine.model.Rol;
import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {
 
    @Autowired
    private UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    
    public UsuarioService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encriptar(String contra) {
        return passwordEncoder.encode(contra);
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByCorreo(email).orElse(null);
    }

    public boolean verificarContraseña(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private UsuarioDTO convertirADTO(Usuario usuario){
        return new UsuarioDTO(usuario.getCodUsuario(), usuario.getNombre(), usuario.getApellido(),
         usuario.getCorreo(), usuario.getNumTelefono(), usuario.getContrasenya(), usuario.getRol());
    }

    private Usuario ConvertirAEntidad(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioRepository.findById(usuarioDTO.getCodUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setNumTelefono(usuarioDTO.getNumTelefono());
        usuario.setRol(usuarioDTO.getRol());

        return usuario;
    }

    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO getUsuarioById(Long id) {
        return usuarioRepository.findById(id).
        map(this::convertirADTO) 
        .orElse(null);    
    }

    
    public UsuarioDTO crearUsuario(Usuario usuario) {
        // Verificar si el correo ya está registrado en la base de datos
        Optional<Usuario> usuarioExistente = usuarioRepository.findByCorreo(usuario.getCorreo());
    
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }
    
        // Validar que el ID del usuario es nulo (ya que es un nuevo usuario)
        if (usuario.getCodUsuario() != null) {
            throw new IllegalArgumentException("El ID del usuario no debe estar presente al crear un nuevo usuario");
        }
    
        // Establecer el rol predeterminado de 'USUARIO'
        usuario.setRol(Rol.USUARIO);
    
        // Encriptar la contraseña antes de guardarla
        System.out.println("Contraseña antes de encriptar: " + usuario.getContrasenya());
        usuario.setContrasenya(encriptar(usuario.getContrasenya()));
        System.out.println("Contraseña después de encriptar: " + usuario.getContrasenya());
    
        // Guardar el nuevo usuario en la base de datos
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
    
        // Devolver el UsuarioDTO con los datos del usuario guardado
        return convertirADTO(usuarioGuardado);
    }
    

    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO UsuarioDTO) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setApellido(UsuarioDTO.getApellido());
            usuario.setNombre(UsuarioDTO.getNombre());
            // usuario.setCorreo(UsuarioDTO.getCorreo());
            usuario.setNumTelefono(UsuarioDTO.getNumTelefono());
            usuario.setRol(UsuarioDTO.getRol());

            Usuario usuarioActualizado = usuarioRepository.save(usuario);
            return convertirADTO(usuarioActualizado);
        }
        throw new EntityNotFoundException("No se ha encontrado compra con ID " + id);
    }

    public void borrarUsuario(Long id){
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("No existe usuario con id: " + id);
        }
    }
}
