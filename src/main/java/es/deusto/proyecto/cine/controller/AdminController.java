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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.deusto.proyecto.cine.dto.EmisionDTO;
import es.deusto.proyecto.cine.dto.PeliculaDTO;
import es.deusto.proyecto.cine.dto.SalaDTO;
import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.repository.UsuarioRepository;
import es.deusto.proyecto.cine.service.EmisionService;
// import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.service.PeliculaService;
import es.deusto.proyecto.cine.service.SalaService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PeliculaService peliculaService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private final EmisionService emisionService;

    private final SalaService salaService;

    public AdminController(PeliculaService peliculaService, EmisionService emisionService, SalaService salaService) {
        this.salaService = salaService;
        this.emisionService = emisionService;
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
    public String borrarPelicula(@PathVariable Long id) {
        peliculaService.borrarPelicula(id);
        return "redirect:/admin/peliculas";
    }

    @PutMapping("/peliculas/editar")
    public String actualizarPelicula(@ModelAttribute PeliculaDTO peliculaDTO) {
        peliculaService.actualizarPelicula(peliculaDTO.getCodPelicula(), peliculaDTO);
        return "redirect:/admin/peliculas";
    }

    @GetMapping("/peliculas/editar/{id}")
    public String editarPelicula(@PathVariable Long id, Model model) {
        PeliculaDTO pelicula = peliculaService.getPeliculaById(id);
        model.addAttribute("pelicula", pelicula);
        return "admin_pelicula_editar";
    }

    @GetMapping("/emisiones")
    public String gestionarEmisiones(Model model) {
        model.addAttribute("nuevaEmision", new EmisionDTO());

        // Aquí asumimos que tienes un método que devuelve entidades Pelicula
        List<PeliculaDTO> peliculas = peliculaService.getAllPeliculas(); 
        List<SalaDTO> salas = salaService.getAllSalas();

        model.addAttribute("peliculas", peliculas);
        model.addAttribute("salas", salas);

        return "admin_emisiones";
    }

    @PostMapping("/emisiones/programar")
    public String programarEmision(@ModelAttribute EmisionDTO emisionDTO) {
        emisionService.crearEmision(emisionDTO);
        return "redirect:/admin/emisiones";
        // Emision emision = new Emision();
        // emision.setDateTime(emisionDTO.getFecha());

        // Pelicula pelicula = peliculaService.getPeliculaByTitulo(emisionDTO.getNomPelicula());
        // emision.setPelicula(pelicula);

        // Sala sala = salaService.getSalaByNumero(emisionDTO.getNumSala());
        // emision.setSala(sala);

        // emisionService.guardar(emision);
        // return "redirect:/admin/emisiones";
    }
}