package es.deusto.proyecto.cine.service;

import es.deusto.proyecto.cine.dto.PeliculaDTO;
import es.deusto.proyecto.cine.model.Pelicula;
import es.deusto.proyecto.cine.repository.PeliculaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las películas del sistema.
 * 
 * Este servicio proporciona funcionalidades para crear,
 * actualizar, eliminar y consultar películas.
 */
@Service
public class PeliculaService {
    
    @Autowired
    private PeliculaRepository peliculaRepository; 

    /**
     * Convierte una entidad Pelicula a un objeto DTO.
     *
     * @param pelicula objeto de la pelicula
     * @return el objeto DTO de la pelicula
     */
    public PeliculaDTO convertirADTO(Pelicula pelicula){
        return new PeliculaDTO(pelicula.getCodPelicula(), pelicula.getTitulo(), pelicula.getGenero(), pelicula.getDuracion(),
         pelicula.getDirector(), pelicula.getSinopsis());
    }

    /**
     * Convierte un objeto DTO de Pelicula a una entidad Pelicula.
     *
     * @param peliculaDTO objeto DTO de la pelicula
     * @return la entidad Pelicula
     */
    public Pelicula convertirAEntidad(PeliculaDTO peliculaDTO){
        Pelicula pelicula = new Pelicula();
        pelicula.setCodPelicula(peliculaDTO.getCodPelicula());
        pelicula.setTitulo(peliculaDTO.getTitulo());
        pelicula.setGenero(peliculaDTO.getGenero());
        pelicula.setDirector(peliculaDTO.getDirector());
        pelicula.setDuracion(peliculaDTO.getDuracion());
        pelicula.setSinopsis(peliculaDTO.getSinopsis());

        return pelicula;
    }

    /**
     * Obtiene una lista de todas las películas en el sistema.
     *
     * @return una lista de objetos DTO de películas
     */
    public List<PeliculaDTO> getAllPeliculas() {
        return peliculaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /* PARA GENERO Y NOMBRE */
    /**
     * Busca películas por nombre y género.
     *
     * @param nombre el nombre de la película
     * @param genero el género de la película
     * @return una lista de objetos DTO de películas que coinciden con los criterios de búsqueda
     */
    public List<PeliculaDTO> buscarPeliculas(String nombre, String genero) {
        return peliculaRepository.findAll().stream()
            .filter(p -> (genero == null || genero.isEmpty() || p.getGenero().equalsIgnoreCase(genero)))
            .filter(p -> (nombre == null || nombre.isEmpty() || p.getTitulo().toLowerCase().contains(nombre.toLowerCase())))
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }
    
    /*IGUAL HAY QUE BORRARLO SI LO OTRO HACE */
    /**
     * Busca películas por género.
     *
     * @param genero el género de la película
     * @return una lista de objetos DTO de películas que coinciden con el género
     */
    public List<PeliculaDTO> getPeliculasByGenero(String genero) {
        return peliculaRepository.findByGenero(genero).stream()
        .map(this::convertirADTO)
        .collect(Collectors.toList());
    }

    /**
     * Busca una película por su ID.
     *
     * @param id el ID de la película
     * @return el objeto DTO de la película, o null si no se encuentra
     */
    public PeliculaDTO getPeliculaById(Long id) {
        return peliculaRepository.findById(id)
                .map(this::convertirADTO) 
                .orElse(null);            
    }

    /**
     * Busca una película por su título.
     *
     * @param titulo el título de la película
     * @return la entidad Pelicula, o null si no se encuentra
     */
    public Pelicula getPeliculaByTitulo(String titulo) {
        return peliculaRepository.findByTitulo(titulo).orElse(null);
    }

    /**
     * Guarda una película en el sistema.
     *
     * @param pelicula la entidad Pelicula a guardar
     */
    public PeliculaDTO crearPelicula(PeliculaDTO peliculaDTO){
        Pelicula pelicula = convertirAEntidad(peliculaDTO);
        Pelicula peliculaGuardada = peliculaRepository.save(pelicula);
        return convertirADTO(peliculaGuardada);
    }
    
    /**
     * Actualiza una película existente en el sistema.
     *
     * @param id el ID de la película a actualizar
     * @param peliculaDTO objeto DTO con los nuevos datos
     * @return el objeto DTO de la película actualizada
     */
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

    /**
     * Elimina una película por su ID.
     *
     * @param id el ID de la película a eliminar
     */
    @Transactional
    public void borrarPelicula(Long id){
        Optional<Pelicula> pelicula = peliculaRepository.findByCodPelicula(id); 
        System.out.println("PELICULA:" + pelicula.get().getTitulo());
        peliculaRepository.deleteByCodPelicula(id);
    }
}
