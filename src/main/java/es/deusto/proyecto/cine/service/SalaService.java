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

@Service
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;  

    private SalaDTO convertirADTO(Sala sala){
        return new SalaDTO(sala.getCodSala(), sala.getNumero(), sala.getCapacidad());
    }

    private Sala ConvertirAEntidad(SalaDTO salaDTO){
        Sala sala = new Sala();
        sala.setCodSala(salaDTO.getCodSala());
        sala.setNumero(salaDTO.getNumero());
        sala.setCapacidad(salaDTO.getCapacidad());

        return sala;
    }

    public List<SalaDTO> getAllSalas() {
        return salaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public SalaDTO getSalaById(Long id) {
        return salaRepository.findById(id)
                .map(this::convertirADTO) 
                .orElse(null); 
    }

    public SalaDTO crearSala(SalaDTO salaDTO){
        Sala sala = ConvertirAEntidad(salaDTO);
        Sala salaGuardada = salaRepository.save(sala);
        return convertirADTO(salaGuardada);
    }

    public SalaDTO actualizarSala(Long id, SalaDTO actualizarSala) {
        Optional<Sala> salaExistente = salaRepository.findById(id);

        if (salaExistente.isPresent()) {
            Sala sala = salaExistente.get();
            sala.setCapacidad(actualizarSala.getCapacidad());
            sala.setNumero(actualizarSala.getNumero());

            Sala salaActualizada = salaRepository.save(sala);
            return convertirADTO(salaActualizada);
        }
        throw new EntityNotFoundException("No se ha encontrado compra con ID " + id);
    }

    public void borrarSala(Long id){
        if (salaRepository.existsById(id)) {
            salaRepository.deleteById(id);
        } else {
            throw new RuntimeException("No existe sala con id: " + id);
        }
    }
}
