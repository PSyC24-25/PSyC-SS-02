package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.dto.PeliculaDTO;
import es.deusto.proyecto.cine.service.PeliculaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import java.util.List;
// import java.util.Optional;

@Controller
@RequestMapping("/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    // @GetMapping
    // public List<PeliculaDTO> getAllPeliculas() {
    //     return peliculaService.getAllPeliculas();
    // }
    @GetMapping
    public String getAllPeliculas(Model model) {
        List<PeliculaDTO> peliculas = peliculaService.getAllPeliculas();
        System.out.println("Films retrieved: " + peliculas);
        System.out.println("Films count: " + peliculas.size());
        model.addAttribute("peliculas", peliculas);
        return "peliculas";
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeliculaDTO> getPeliculaById(@PathVariable Long id) {
        PeliculaDTO pelicula = peliculaService.getPeliculaById(id);

        if(pelicula != null){
            return ResponseEntity.ok(pelicula);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public String crearPelicula(@ModelAttribute PeliculaDTO peliculaDTO) {
        peliculaService.crearPelicula(peliculaDTO);
        return "redirect:/peliculas";
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeliculaDTO> actualizarPelicula(@PathVariable Long id, @RequestBody PeliculaDTO datosPeliculaDTO) {
        try {
            PeliculaDTO peliculaActualizada = peliculaService.actualizarPelicula(id, datosPeliculaDTO);
            return ResponseEntity.ok(peliculaActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarPelicula(@PathVariable Long id) {
        if (peliculaService.getPeliculaById(id) != null){
            peliculaService.borrarPelicula(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
