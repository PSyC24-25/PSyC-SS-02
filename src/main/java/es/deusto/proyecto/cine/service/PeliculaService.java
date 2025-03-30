package es.deusto.proyecto.cine.service;

import es.deusto.proyecto.cine.dto.PeliculaDTO;
import es.deusto.proyecto.cine.model.Pelicula;
import es.deusto.proyecto.cine.repository.PeliculaRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PeliculaService {
    
    @Autowired
    private PeliculaRepository peliculaRepository; 

    private PeliculaDTO convertirADTO(Pelicula pelicula){
        return new PeliculaDTO(pelicula.getCodPelicula(), pelicula.getTitulo(), pelicula.getGenero(), pelicula.getDuracion(),
         pelicula.getDirector(), pelicula.getSinopsis());
    }

    private Pelicula ConvertirAEntidad(PeliculaDTO peliculaDTO){
        Pelicula pelicula = new Pelicula();
        pelicula.setCodPelicula(peliculaDTO.getCodPelicula());
        pelicula.setTitulo(peliculaDTO.getTitulo());
        pelicula.setGenero(peliculaDTO.getGenero());
        pelicula.setDirector(peliculaDTO.getDirector());
        pelicula.setDuracion(peliculaDTO.getDuracion());
        pelicula.setSinopsis(peliculaDTO.getSinopsis());

        return pelicula;
    }

    public List<PeliculaDTO> getAllPeliculas() {
        return peliculaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public PeliculaDTO getPeliculaById(Long id) {
        return peliculaRepository.findById(id)
                .map(this::convertirADTO) 
                .orElse(null);            
    }

    public PeliculaDTO crearPelicula(PeliculaDTO peliculaDTO){
        Pelicula pelicula = ConvertirAEntidad(peliculaDTO);
        Pelicula peliculaGuardada = peliculaRepository.save(pelicula);
        return convertirADTO(peliculaGuardada);
    }
    
    public PeliculaDTO actualizarPelicula(Long id, PeliculaDTO peliculaDTO) {
        Optional<Pelicula> peliculaExistente = peliculaRepository.findById(id);

        if (peliculaExistente.isPresent()) {
            Pelicula pelicula = peliculaExistente.get();
            pelicula.setTitulo(peliculaDTO.getTitulo());
            pelicula.setGenero(peliculaDTO.getGenero());
            pelicula.setDuracion(peliculaDTO.getDuracion());
            pelicula.setDirector(peliculaDTO.getDirector());
            pelicula.setSinopsis(peliculaDTO.getSinopsis());

            Pelicula peliculaActualizada = peliculaRepository.save(pelicula);
            return convertirADTO(peliculaActualizada);
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
