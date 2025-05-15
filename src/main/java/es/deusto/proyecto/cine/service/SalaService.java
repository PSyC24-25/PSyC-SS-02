package es.deusto.proyecto.cine.service;

import es.deusto.proyecto.cine.dto.SalaDTO;
import es.deusto.proyecto.cine.model.Sala;
import es.deusto.proyecto.cine.repository.SalaRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las salas del sistema.
 * 
 * Este servicio proporciona funcionalidades para crear,
 * actualizar, eliminar y consultar salas.
 */
@Service
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;  

    /**
     * Convierte una entidad Sala a un objeto DTO.
     *
     * @param sala objeto de la sala
     * @return el objeto DTO de la sala
     */
    public SalaDTO convertirADTO(Sala sala){
        return new SalaDTO(sala.getCodSala(), sala.getNumero(), sala.getCapacidad(), sala.getColumnas());
    }

    /**
     * Convierte un objeto DTO de Sala a una entidad Sala.
     *
     * @param salaDTO objeto DTO de la sala
     * @return la entidad Sala
     */
    public Sala ConvertirAEntidad(SalaDTO salaDTO){
        Sala sala = new Sala();
        sala.setCodSala(salaDTO.getCodSala());
        sala.setNumero(salaDTO.getNumero());
        sala.setCapacidad(salaDTO.getCapacidad());
        sala.setColumnas(salaDTO.getColumnas());

        return sala;
    }

    /**
     * Obtiene una lista de todas las salas en el sistema.
     *
     * @return una lista de objetos DTO de salas
     */
    public List<Sala> obtenerTodas() {
    return salaRepository.findAll();
    }

    /**
     * Obtiene una lista de todas las salas en el sistema.
     *
     * @return una lista de objetos DTO de salas
     */
    public List<SalaDTO> getAllSalas() {
        return salaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una sala por su ID.
     *
     * @param id el ID de la sala
     * @return el objeto DTO de la sala, o null si no se encuentra
     */
    public SalaDTO getSalaById(Long id) {
        return salaRepository.findById(id)
                .map(this::convertirADTO) 
                .orElse(null); 
    }

    /**
     * Busca una sala por su número.
     *
     * @param numero el número de la sala
     * @return la entidad Sala, o null si no se encuentra
     */
    public Sala getSalaByNumero(int numero) {
        return salaRepository.findByNumero(numero).orElse(null);
    }

    /**
     * Crea una nueva sala en el sistema.
     *
     * @param salaDTO objeto DTO de la sala a crear
     * @return el objeto DTO de la sala creada
     */
    public SalaDTO crearSala(SalaDTO salaDTO){
        Sala salaExistente = getSalaByNumero(salaDTO.getNumero());
        if (salaExistente != null) {
            throw new RuntimeException("Ya existe una sala con el número: " + salaDTO.getNumero());
        }
        Sala sala = ConvertirAEntidad(salaDTO);
        Sala salaGuardada = salaRepository.save(sala);
        return convertirADTO(salaGuardada);
    }

    /**
     * Actualiza una sala existente en el sistema.
     *
     * @param id el ID de la sala a actualizar
     * @param actualizarSala el objeto DTO con los nuevos datos
     * @return el objeto DTO de la sala actualizada
     */
    public SalaDTO actualizarSala(Long id, SalaDTO actualizarSala) {
        Optional<Sala> salaExistente = salaRepository.findById(id);

        if (salaExistente.isPresent()) {
            Sala sala = salaExistente.get();
            sala.setCapacidad(actualizarSala.getCapacidad());
            sala.setNumero(actualizarSala.getNumero());
            sala.setColumnas(actualizarSala.getColumnas());

            Sala salaActualizada = salaRepository.save(sala);
            return convertirADTO(salaActualizada);
        }
        throw new EntityNotFoundException("No se ha encontrado compra con ID " + id);
    }

    /**
     * Elimina una sala por su ID.
     *
     * @param id el ID de la sala a eliminar
     */
    public void borrarSala(Long id){
        if (salaRepository.existsById(id)) {
            salaRepository.deleteById(id);
        } else {
            throw new RuntimeException("No existe sala con id: " + id);
        }
    }
}
