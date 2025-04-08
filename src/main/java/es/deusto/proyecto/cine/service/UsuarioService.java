package es.deusto.proyecto.cine.service;

import es.deusto.proyecto.cine.dto.UsuarioDTO;
import es.deusto.proyecto.cine.model.Rol;
import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return new UsuarioDTO(usuario.getCodUsuario(), usuario.getNombre(), usuario.getApellido(), usuario.getCorreo(), usuario.getNumTelefono(), usuario.getContrasenya());
    }

    private Usuario ConvertirAEntidad(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioRepository.findById(usuarioDTO.getCodUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setNumTelefono(usuarioDTO.getNumTelefono());

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

    
    public UsuarioDTO crearUsuario(Usuario usuario){
        Optional<Usuario> us = usuarioRepository.findByCorreo("pedro@gmail.com");
        System.out.println("Resultado de la búsqueda: " + us.get().getNombre());
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new RuntimeException("Usuario ya registrado");
        }
        if (usuario.getCodUsuario() != null) {
            throw new IllegalArgumentException("ID nulo");
        }
        usuario.setRol(Rol.USUARIO);
        System.out.println("Contra antes de hashear " + usuario.getContrasenya());
        usuario.setContrasenya(encriptar(usuario.getContrasenya()));
        System.out.println("Contra despues de hashear " + usuario.getContrasenya());
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioGuardado);
    }

    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO UsuarioDTO) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setApellido(UsuarioDTO.getApellido());
            usuario.setNombre(UsuarioDTO.getNombre());
            usuario.setCorreo(UsuarioDTO.getCorreo());

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
