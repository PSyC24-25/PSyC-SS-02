package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.getUsuarioById(id);

        if(usuario.isPresent()){
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Usuario crearCompra(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario datosUsuario) {
        try {
            Usuario usuarioActualizada = usuarioService.actualizarUsuario(id, datosUsuario);
            return ResponseEntity.ok(usuarioActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> borrarUsuario(@PathVariable Long id) {
        if (usuarioService.getUsuarioById(id).isPresent()){
            usuarioService.borrarUsuario(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
