package es.deusto.proyecto.cine.service;

import es.deusto.proyecto.cine.model.Emision;
import es.deusto.proyecto.cine.repository.EmisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmisionService {

    @Autowired
    private EmisionRepository emisionRepository;

    public List<Emision> getAllEmisiones() {
        return emisionRepository.findAll();
    }

    public Emision getEmisionById(Long id) {
        return emisionRepository.findById(id).orElse(null);
    }
}