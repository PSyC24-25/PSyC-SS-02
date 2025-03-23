package es.deusto.proyecto.cine.service;

import es.deusto.proyecto.cine.model.Sala;
import es.deusto.proyecto.cine.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;  


    public List<Sala> getAllSalas() {
        return salaRepository.findAll();
    }

    public Sala getSalaById(Long id) {
        return salaRepository.findById(id).orElse(null); 
    }
}
