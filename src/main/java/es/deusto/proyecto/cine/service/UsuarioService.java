package es.deusto.proyecto.cine.service;

import es.deusto.proyecto.cine.dto.UsuarioDTO;
import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
 
    @Autowired
    private UsuarioRepository usuarioRepository;

    private UsuarioDTO convertirADTO(Usuario usuario){
        return new UsuarioDTO(usuario.getCodUsuario(), usuario.getNombre(), usuario.getApellido(), usuario.getCorreo());
    }

    private Usuario ConvertirAEntidad(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioRepository.findById(usuarioDTO.getCodUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setCorreo(usuarioDTO.getCorreo());

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

    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = ConvertirAEntidad(usuarioDTO);
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
