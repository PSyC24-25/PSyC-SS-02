package es.deusto.proyecto.cine.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.deusto.proyecto.cine.model.Usuario;

@Controller
public class IndiceController {

    @GetMapping("/")
    public String indice(@AuthenticationPrincipal Usuario usuario, Model model) {
        model.addAttribute("usuario", usuario);
        return "index";
    }
}
