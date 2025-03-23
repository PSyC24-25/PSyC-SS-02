package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.model.Sala;
import es.deusto.proyecto.cine.service.SalaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SalaController {
    
    @Autowired
    private SalaService salaService;

    @GetMapping("/salas")
    public List<Sala> getAllSalas() {
        return salaService.getAllSalas();
    }

    @GetMapping("/salas/{id}")
    public Sala getSalaById(@PathVariable Long id) {
        return salaService.getSalaById(id);
    }
}
