package es.deusto.proyecto.cine.service;

import es.deusto.proyecto.cine.model.Sala;
import es.deusto.proyecto.cine.repository.SalaRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;  


    public List<Sala> getAllSalas() {
        return salaRepository.findAll();
    }

    public Optional<Sala> getSalaById(Long id) {
        return salaRepository.findById(id); 
    }

    public Sala crearSala(Sala sala){
        return salaRepository.save(sala);
    }

    public Sala actualizarSala(Long id, Sala actualizarSala) {
        Optional<Sala> salaExistente = salaRepository.findById(id);
        if (salaExistente.isPresent()) {
            Sala sala = salaExistente.get();
            sala.setCapacidad(actualizarSala.getCapacidad());
            sala.setEmisiones(actualizarSala.getEmisiones());
            sala.setNumero(actualizarSala.getNumero());
            return salaRepository.save(sala);
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
