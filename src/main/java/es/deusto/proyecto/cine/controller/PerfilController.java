package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.model.Compra;
import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.repository.CompraRepository;
import es.deusto.proyecto.cine.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class PerfilController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CompraRepository compraRepository;

    @GetMapping("/perfil")
    public String verPerfil(Model model, Principal principal) {
        // Obtener el correo del usuario autenticado
        String correo = principal.getName();

        // Buscar usuario por correo (viene como Optional)
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);

        if (usuario == null) {
            return "redirect:/autenticacion/login";
        }

        // Obtener compras del usuario
        List<Compra> compras = compraRepository.findByUsuarioCodUsuario(usuario.getCodUsuario());

        // Pasar datos al modelo
        model.addAttribute("usuario", usuario);
        model.addAttribute("compras", compras);

        return "perfil";
    }

}
