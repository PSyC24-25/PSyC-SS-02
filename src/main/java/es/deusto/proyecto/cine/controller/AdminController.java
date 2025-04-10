package es.deusto.proyecto.cine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.deusto.proyecto.cine.dto.PeliculaDTO;
import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.repository.UsuarioRepository;
// import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.service.PeliculaService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PeliculaService peliculaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmisionService emisionService;

    @Autowired
    private SalaService salaService;

    public AdminController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    // @PreAuthorize("hasRole('ADMIN')") @AuthenticationPrincipal Usuario usuario, Model model
    @GetMapping
    public String panelAdmin(Authentication authentication, Model model) {
        // model.addAttribute("nombre", usuario.getNombre());
        if (authentication != null) {
            String nombre = authentication.getName();
            Usuario usuario = usuarioRepository.findByCorreo(nombre).orElse(null);
            model.addAttribute("usuario", usuario);
        }
        return "panel_admin";  
    }

    @GetMapping("/peliculas")
    public String getPeliculasAdmin(Model model) {
        List<PeliculaDTO> peliculas = peliculaService.getAllPeliculas();
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("nuevaPelicula", new PeliculaDTO());
        return "admin_peliculas";
    }

    @PostMapping("/peliculas/agregar")
    public String agregarPelicula(@ModelAttribute PeliculaDTO peliculaDTO) {
        peliculaService.crearPelicula(peliculaDTO);
        return "redirect:/admin/peliculas";
    }

    @DeleteMapping("/peliculas/borrar/{id}")
    public String deleteFilm(@PathVariable Long id) {
        peliculaService.borrarPelicula(id);
        return "redirect:/admin/peliculas";
    }

    @GetMapping("/emisiones")
    public String gestionarEmisiones(Model model) {
        model.addAttribute("nuevaEmision", new Emision());

        // Aquí asumimos que tienes un método que devuelve entidades Pelicula
        List<Pelicula> peliculas = peliculaService.getAllPeliculasRaw(); 
        List<Sala> salas = salaService.obtenerTodas();

        model.addAttribute("peliculas", peliculas);
        model.addAttribute("salas", salas);

        return "admin_emisiones";
    }

    @PostMapping("/emisiones/programar")
    public String programarEmision(@ModelAttribute("nuevaEmision") Emision emision) {
        emisionService.guardar(emision);
        return "redirect:/admin/emisiones";
    }
}