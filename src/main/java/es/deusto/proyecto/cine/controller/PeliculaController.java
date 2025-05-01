package es.deusto.proyecto.cine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.deusto.proyecto.cine.dto.PeliculaDTO;
import es.deusto.proyecto.cine.service.PeliculaService;
// import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public String getAllPeliculas(@RequestParam(required = false) String genero,
                                  @RequestParam(required = false) String nombre, 
                                  Model model,
                                  @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        List<PeliculaDTO> peliculas = peliculaService.buscarPeliculas(nombre, genero);
        
        // Verifica las películas recuperadas
        System.out.println("Películas obtenidas: " + peliculas);
    
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("genero", genero);
        model.addAttribute("nombre", nombre);
    
        if ("XMLHttpRequest".equals(requestedWith)) {
            // Asegúrate de que se devuelve solo el fragmento y no toda la página
            return "fragments/tablaPeliculas :: tabla";
        }
    
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
