package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.model.Sala;
import es.deusto.proyecto.cine.service.SalaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SalaController {
    
    @Autowired
    private SalaService salaService;

    @GetMapping("/salas")
    public List<Sala> getAllSalas() {
        return salaService.getAllSalas();
    }

    @GetMapping("/salas/{id}")
    public ResponseEntity<Sala> getSalaById(@PathVariable Long id) {
        Optional<Sala> sala = salaService.getSalaById(id);

        if(sala.isPresent()){
            return ResponseEntity.ok(sala.get());
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping
    public Sala crearSala(@RequestBody Sala sala) {
        return salaService.crearSala(sala);
    }

    @PutMapping("/salas/{id}")
    public ResponseEntity<Sala> actualizarSala(@PathVariable Long id, @RequestBody Sala datosSala) {
        try {
            Sala salaActualizada = salaService.actualizarSala(id, datosSala);
            return ResponseEntity.ok(salaActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/salas/{id}")
    public ResponseEntity<Void> borrarSala(@PathVariable Long id) {
        if (salaService.getSalaById(id).isPresent()){
            salaService.borrarSala(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
