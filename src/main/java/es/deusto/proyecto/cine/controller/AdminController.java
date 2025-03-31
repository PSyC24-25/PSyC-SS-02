package es.deusto.proyecto.cine.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.deusto.proyecto.cine.dto.PeliculaDTO;
import es.deusto.proyecto.cine.service.PeliculaService;

@Controller
@RequestMapping("/admin/peliculas")
public class AdminController {

    private final PeliculaService peliculaService;

    public AdminController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @GetMapping
    public String getPeliculasAdmin(Model model) {
        List<PeliculaDTO> films = peliculaService.getAllPeliculas();
        model.addAttribute("films", films);
        return "admin_peliculas";
    }

    @PostMapping
    public String addPelicula(@ModelAttribute PeliculaDTO peliculaDTO) {
        peliculaService.crearPelicula(peliculaDTO);
        return "redirect:/admin/peliculas";
    }
}