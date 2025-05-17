package es.deusto.proyecto.cine.service;

import es.deusto.proyecto.cine.dto.EmisionDTO;
import es.deusto.proyecto.cine.model.Emision;
import es.deusto.proyecto.cine.model.Pelicula;
import es.deusto.proyecto.cine.model.Sala;
import es.deusto.proyecto.cine.repository.EmisionRepository;
import es.deusto.proyecto.cine.repository.PeliculaRepository;
import es.deusto.proyecto.cine.repository.SalaRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las emisiones del sistema.
 * 
 * Este servicio proporciona funcionalidades para crear,
 * actualizar, eliminar y consultar emisiones.
 */
@Service
public class EmisionService {

    @Autowired
    private EmisionRepository emisionRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;

    /**
     * Convierte una entidad Emision a un objeto DTO.
     *
     * @param emision objeto de la emision
     * @return el objeto DTO de la emision
     */
    private EmisionDTO convertirADTO(Emision emision){
        if (emision.getPelicula() == null) {
            throw new RuntimeException("Pelicula no encontrada");
        }
        if (emision.getSala() == null) {
            throw new RuntimeException("Sala no encontrada");
        }
        return new EmisionDTO(emision.getCodEmision(), emision.getFecha(), emision.getPelicula().getTitulo()
        , emision.getSala().getNumero());
    }

    /**
     * Convierte un objeto DTO de Emision a una entidad Emision.
     *
     * @param emisionDTO objeto DTO de la emision
     * @return la entidad Emision
     */
    public Emision ConvertirAEntidad(EmisionDTO emisionDTO){
        Pelicula pelicula = peliculaRepository.findByTitulo(emisionDTO.getNomPelicula())
            .orElseThrow(() -> new RuntimeException("Pelicula no encontrada"));
        Sala sala = salaRepository.findByNumero(emisionDTO.getNumSala())
            .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        Emision emision = new Emision();
        emision.setCodEmision(emisionDTO.getCodEmision());
        emision.setFecha(emisionDTO.getFecha());
        emision.setPelicula(pelicula);
        emision.setSala(sala);

        return emision;
    }

    /**
     * Obtiene todas las emisiones del sistema.
     *
     * @return una lista de objetos DTO de emisiones
     */
    public List<EmisionDTO> getAllEmisiones() {
        return emisionRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una emisión por su ID.
     *
     * @param id el ID de la emisión
     * @return el objeto DTO de la emisión, o null si no se encuentra
     */
    public EmisionDTO getEmisionById(Long id) {
        return emisionRepository.findById(id)
        .map(this::convertirADTO) 
        .orElse(null);    
    }

    /**
     * Guarda una emisión en el sistema.
     *
     * @param emision la entidad Emision a guardar
     */
    public void guardar(Emision emision) {
    emisionRepository.save(emision);
    }   

    /**
     * Crea una nueva emisión en el sistema.
     *
     * @param emisionDTO objeto DTO de la emisión a crear
     * @return el objeto DTO de la emisión creada
     */
    public EmisionDTO crearEmision(EmisionDTO emisionDTO){
        Emision emision = ConvertirAEntidad(emisionDTO);
        Emision emisionGuardada = emisionRepository.save(emision);
        return convertirADTO(emisionGuardada);
    }

    /**
     * Actualiza una emisión existente en el sistema.
     *
     * @param id el ID de la emisión a actualizar
     * @param actualizarEmisionDTO objeto DTO con los nuevos datos
     * @return el objeto DTO de la emisión actualizada
     */
    public EmisionDTO actualizarEmision(Long id, EmisionDTO actualizarEmisionDTO) {
        Optional<Emision> emisionExistente = emisionRepository.findById(id);

        if (emisionExistente.isPresent()) {
            Emision emision = emisionExistente.get();
            emision.setFecha(actualizarEmisionDTO.getFecha());

            Pelicula pelicula = peliculaRepository.findByTitulo(actualizarEmisionDTO.getNomPelicula())
                .orElseThrow(() -> new RuntimeException("Pelicula no encontrada"));
            emision.setPelicula(pelicula);
            Sala sala = salaRepository.findByNumero(actualizarEmisionDTO.getNumSala())
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));
            emision.setSala(sala);

            Emision emisionActualizada = emisionRepository.save(emision);
            return convertirADTO(emisionActualizada);
        }
        throw new EntityNotFoundException("No se ha encontrado emision con ID " + id);
    }

    /**
     * Elimina una emisión por su ID.
     *
     * @param id el ID de la emisión a eliminar
     */
    public void borrarEmision(Long id){
        if (emisionRepository.existsById(id)) {
            emisionRepository.deleteById(id);
        } else {
            throw new RuntimeException("No existe emision con id: " + id);
        }
    }
}