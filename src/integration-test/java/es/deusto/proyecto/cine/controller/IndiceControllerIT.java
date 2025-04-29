package es.deusto.proyecto.cine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.repository.UsuarioRepository;


@Controller
public class IndiceController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/")
    public String indice(Authentication authentication, Model model) {
        if (authentication != null) {
            String nombre = authentication.getName();
            Usuario usuario = usuarioRepository.findByCorreo(nombre).orElse(null);
            model.addAttribute("usuario", usuario);
        }
        return "index";
    }
}
