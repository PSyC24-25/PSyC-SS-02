package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.model.Emision;
import es.deusto.proyecto.cine.service.EmisionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmisionController {
    
    @Autowired
    private EmisionService emisionService;

    @GetMapping("/emisiones")
    public List<Emision> getAllEmisiones() {
        return emisionService.getAllEmisiones();
    }

    @GetMapping("/emisiones/{id}")
    public ResponseEntity<Emision> getEmisionById(@PathVariable Long id) {
        Optional<Emision> emision = emisionService.getEmisionById(id);

        if(emision.isPresent()){
            return ResponseEntity.ok(emision.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Emision crearEmision(@RequestBody Emision emision) {
        return emisionService.crearEmision(emision);
    }

    @PutMapping("/emisiones/{id}")
    public ResponseEntity<Emision> actualizarEmision(@PathVariable Long id, @RequestBody Emision datosEmision) {
        try {
            Emision emisionActualizada = emisionService.actualizarEmision(id, datosEmision);
            return ResponseEntity.ok(emisionActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/emisiones/{id}")
    public ResponseEntity<Void> borrarEmision(@PathVariable Long id) {
        if (emisionService.getEmisionById(id).isPresent()){
            emisionService.borrarEmision(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
