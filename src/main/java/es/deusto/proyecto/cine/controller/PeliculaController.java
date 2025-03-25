package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.model.Pelicula;
import es.deusto.proyecto.cine.service.PeliculaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping("/peliculas")
    public List<Pelicula> getAllPeliculas() {
        return peliculaService.getAllPeliculas();
    }

    @GetMapping("/peliculas/{id}")
    public ResponseEntity<Pelicula> getPeliculaById(@PathVariable Long id) {
        Optional<Pelicula> pelicula = peliculaService.getPeliculaById(id);

        if(pelicula.isPresent()){
            return ResponseEntity.ok(pelicula.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Pelicula crearPelicula(@RequestBody Pelicula pelicula) {
        return peliculaService.crearPelicula(pelicula);
    }

    @PutMapping("/peliculas/{id}")
    public ResponseEntity<Pelicula> actualizarPelicula(@PathVariable Long id, @RequestBody Pelicula datosPelicula) {
        try {
            Pelicula peliculaActualizada = peliculaService.actualizarPelicula(id, datosPelicula);
            return ResponseEntity.ok(peliculaActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/peliculas/{id}")
    public ResponseEntity<Void> borrarPelicula(@PathVariable Long id) {
        if (peliculaService.getPeliculaById(id).isPresent()){
            peliculaService.borrarPelicula(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
