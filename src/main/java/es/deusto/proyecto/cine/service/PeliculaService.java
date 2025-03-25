package es.deusto.proyecto.cine.service;

import es.deusto.proyecto.cine.model.Pelicula;
import es.deusto.proyecto.cine.repository.PeliculaRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {
    
    @Autowired
    private PeliculaRepository peliculaRepository;  

    public List<Pelicula> getAllPeliculas() {
        return peliculaRepository.findAll();
    }

    public Optional<Pelicula> getPeliculaById(Long id) {
        return peliculaRepository.findById(id); 
    }

    public Pelicula crearPelicula(Pelicula pelicula){
        return peliculaRepository.save(pelicula);
    }
    
    public Pelicula actualizarPelicula(Long id, Pelicula actualizarPelicula) {
        Optional<Pelicula> peliculaExistente = peliculaRepository.findById(id);
        if (peliculaExistente.isPresent()) {
            Pelicula pelicula = peliculaExistente.get();
            pelicula.setTitulo(actualizarPelicula.getTitulo());
            pelicula.setGenero(actualizarPelicula.getGenero());
            pelicula.setDuracion(actualizarPelicula.getDuracion());
            pelicula.setDirector(actualizarPelicula.getDirector());
            pelicula.setSinopsis(actualizarPelicula.getDirector());
            return peliculaRepository.save(pelicula);
        }
        throw new EntityNotFoundException("No se ha encontrado compra con ID " + id);
    }

    public void borrarPelicula(Long id){
        if (peliculaRepository.existsById(id)) {
            peliculaRepository.deleteById(id);
        } else {
            throw new RuntimeException("No existe pelicula con id: " + id);
        }
    }
}
