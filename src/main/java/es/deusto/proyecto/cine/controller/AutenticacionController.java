package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.dto.UsuarioDTO;
import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.service.UsuarioService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de autenticación en el sistema de cine.
 */
@Controller
@RequestMapping("/autenticacion")
public class AutenticacionController {

    private final UsuarioService usuarioService;

    public AutenticacionController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    /**
     * Muestra el formulario de inicio de sesión.
     *
     * @return la vista del formulario de inicio de sesión
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    /**
     * Muestra el formulario de registro de usuario.
     *
     * @param model el modelo para pasar datos a la vista
     * @return la vista del formulario de registro de usuario
     */
    @GetMapping("/registro")
    public String formularioRegistro(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        return "registro";
    }

    /**
     * Procesa el registro de un nuevo usuario.
     *
     * @param usuario el objeto Usuario con los datos del nuevo usuario
     * @return la vista de inicio de sesión
     */
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuarioService.crearUsuario(usuario);
        return "redirect:/autenticacion/login";
    }
}