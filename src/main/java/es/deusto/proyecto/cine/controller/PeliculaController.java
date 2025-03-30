package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.dto.PeliculaDTO;
// import es.deusto.proyecto.cine.model.Pelicula;
import es.deusto.proyecto.cine.service.PeliculaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// import java.util.Optional;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public List<PeliculaDTO> getAllPeliculas() {
        return peliculaService.getAllPeliculas();
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
    public ResponseEntity<PeliculaDTO> crearPelicula(@RequestBody PeliculaDTO peliculaDTO) {
        PeliculaDTO peliculaGuardada = peliculaService.crearPelicula(peliculaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(peliculaGuardada);
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
