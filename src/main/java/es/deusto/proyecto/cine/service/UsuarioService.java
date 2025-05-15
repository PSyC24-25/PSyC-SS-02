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

/**
 * Servicio para gestionar los usuarios del sistema.
 * 
 * Este servicio proporciona funcionalidades para crear,
 * actualizar, eliminar y consultar usuarios. Además,
 * permite encriptar contraseñas y verificar credenciales.
 */
@Service
public class UsuarioService {
 
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Encripta una contraseña utilizando el PasswordEncoder.
     *
     * @param contra la contraseña en texto plano
     * @return la contraseña encriptada
     */
    public String encriptar(String contra) {
        return passwordEncoder.encode(contra);
    }

    /**
     * Verifica si un usuario existe en la base de datos por su correo electrónico.
     *
     * @param email el correo electrónico del usuario
     * @return el objeto Usuario si existe, null en caso contrario
     */
    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByCorreo(email).orElse(null);
    }

    /**
     * Verifica si una contraseña en texto plano coincide con una contraseña encriptada.
     *
     * @param rawPassword la contraseña en texto plano
     * @param encodedPassword la contraseña encriptada
     * @return true si coinciden, false en caso contrario
     */
    public boolean verificarContraseña(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Convierte una entidad Usuario a un objeto DTO.
     *
     * @param usuario objeto de la usuario
     * @return el objeto DTO de la usuario
     */
    public UsuarioDTO convertirADTO(Usuario usuario){
        return new UsuarioDTO(usuario.getCodUsuario(), usuario.getNombre(), usuario.getApellido(),
         usuario.getCorreo(), usuario.getNumTelefono(), usuario.getContrasenya(), usuario.getRol());
    }

    /**
     * Convierte un objeto DTO de Usuario a una entidad Usuario.
     *
     * @param usuarioDTO objeto DTO de la usuario
     * @return la entidad Usuario
     */
    public Usuario ConvertirAEntidad(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioRepository.findById(usuarioDTO.getCodUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setNumTelefono(usuarioDTO.getNumTelefono());
        usuario.setRol(usuarioDTO.getRol());

        return usuario;
    }

    /**
     * Obtiene una lista de todos los usuarios en el sistema.
     *
     * @return una lista de objetos DTO de usuarios
     */
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id el ID del usuario
     * @return el objeto DTO de la usuario, o null si no se encuentra
     */
    public UsuarioDTO getUsuarioById(Long id) {
        return usuarioRepository.findById(id).
        map(this::convertirADTO) 
        .orElse(null);    
    }

    
    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param correo el correo electrónico del usuario
     * @return el objeto DTO de la usuario, o null si no se encuentra
     */
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

        usuario.setContrasenya(encriptar(usuario.getContrasenya()));
    
        // Guardar el nuevo usuario en la base de datos
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
    
        // Devolver el UsuarioDTO con los datos del usuario guardado
        return convertirADTO(usuarioGuardado);
    }
    

    /**
     * Actualiza un usuario existente en el sistema.
     *
     * @param id el ID del usuario a actualizar
     * @param UsuarioDTO objeto DTO con los nuevos datos
     * @return el objeto DTO de la usuario actualizada
     */
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

    /**
     * Elimina un usuario por su ID.
     *
     * @param id el ID del usuario a eliminar
     */
    public void borrarUsuario(Long id){
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("No existe usuario con id: " + id);
        }
    }
}
