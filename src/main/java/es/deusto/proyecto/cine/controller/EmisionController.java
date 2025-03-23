package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.model.Emision;
import es.deusto.proyecto.cine.service.EmisionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmisionController {
    
    @Autowired
    private EmisionService emisionService;

    @GetMapping("/emisiones")
    public List<Emision> getAllEmisiones() {
        return emisionService.getAllEmisiones();
    }

    @GetMapping("/emisiones/{id}")
    public Emision getEmisionById(@PathVariable Long id) {
        return emisionService.getEmisionById(id);
    }
}
