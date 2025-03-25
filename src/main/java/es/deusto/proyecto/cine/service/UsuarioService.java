package es.deusto.proyecto.cine.service;

import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
 
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario crearUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Long id, Usuario actualizarUsuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setApellido(actualizarUsuario.getApellido());
            usuario.setNombre(actualizarUsuario.getNombre());
            usuario.setCompras(actualizarUsuario.getCompras());
            usuario.setContrasenya(actualizarUsuario.getContrasenya());
            usuario.setCorreo(actualizarUsuario.getCorreo());
            usuario.setNumTelefono(actualizarUsuario.getNumTelefono());
            return usuarioRepository.save(usuario);
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
