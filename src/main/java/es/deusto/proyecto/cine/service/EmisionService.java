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

@Service
public class EmisionService {

    @Autowired
    private EmisionRepository emisionRepository;
    private SalaRepository salaRepository;
    private PeliculaRepository peliculaRepository;

    private EmisionDTO convertirADTO(Emision emision){
        return new EmisionDTO(emision.getCodEmision(), emision.getDateTime(), emision.getPelicula().getTitulo()
        , emision.getSala().getNumero());
    }

    private Emision ConvertirAEntidad(EmisionDTO emisionDTO){
        Pelicula pelicula = peliculaRepository.findByTitulo(emisionDTO.getNomPelicula())
            .orElseThrow(() -> new RuntimeException("Pelicula no encontrada"));
        Sala sala = salaRepository.findByNumero(emisionDTO.getNumSala())
            .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        Emision emision = new Emision();
        emision.setCodEmision(emisionDTO.getCodEmision());
        emision.setDateTime(emisionDTO.getFecha());
        emision.setPelicula(pelicula);
        emision.setSala(sala);

        return emision;
    }

    public List<EmisionDTO> getAllEmisiones() {
        return emisionRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public EmisionDTO getEmisionById(Long id) {
        return emisionRepository.findById(id)
        .map(this::convertirADTO) 
        .orElse(null);    
    }

    public EmisionDTO crearEmision(EmisionDTO emisionDTO){
        Emision emision = ConvertirAEntidad(emisionDTO);
        Emision emisionGuardada = emisionRepository.save(emision);
        return convertirADTO(emisionGuardada);
    }

    public EmisionDTO actualizarEmision(Long id, EmisionDTO actualizarEmisionDTO) {
        Optional<Emision> emisionExistente = emisionRepository.findById(id);

        if (emisionExistente.isPresent()) {
            Emision emision = emisionExistente.get();
            emision.setDateTime(actualizarEmisionDTO.getFecha());

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

    public void borrarEmision(Long id){
        if (emisionRepository.existsById(id)) {
            emisionRepository.deleteById(id);
        } else {
            throw new RuntimeException("No existe emision con id: " + id);
        }
    }
}