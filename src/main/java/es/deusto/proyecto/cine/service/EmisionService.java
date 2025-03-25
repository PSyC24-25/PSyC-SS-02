package es.deusto.proyecto.cine.service;

import es.deusto.proyecto.cine.model.Emision;
import es.deusto.proyecto.cine.repository.EmisionRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmisionService {

    @Autowired
    private EmisionRepository emisionRepository;

    public List<Emision> getAllEmisiones() {
        return emisionRepository.findAll();
    }

    public Optional<Emision> getEmisionById(Long id) {
        return emisionRepository.findById(id);
    }

    public Emision crearEmision(Emision emision){
        return emisionRepository.save(emision);
    }

    public Emision actualizarEmision(Long id, Emision actualizarEmision) {
        Optional<Emision> emisionExistente = emisionRepository.findById(id);
        if (emisionExistente.isPresent()) {
            Emision emision = emisionExistente.get();
            emision.setCompras(actualizarEmision.getCompras());
            emision.setDateTime(actualizarEmision.getDateTime());
            emision.setPelicula(actualizarEmision.getPelicula());
            emision.setSala(actualizarEmision.getSala());
            return emisionRepository.save(emision);
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