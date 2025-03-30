package es.deusto.proyecto.cine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndiceController {

    @GetMapping("/")
    public String indice() {
        return "index";
    }
}
