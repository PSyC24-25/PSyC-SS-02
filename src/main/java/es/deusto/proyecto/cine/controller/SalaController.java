package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.dto.SalaDTO;
import es.deusto.proyecto.cine.service.SalaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/salas")
public class SalaController {
    
    @Autowired
    private SalaService salaService;

    @GetMapping
    public List<SalaDTO> getAllSalas() {
        return salaService.getAllSalas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> getSalaById(@PathVariable Long id) {
        SalaDTO sala = salaService.getSalaById(id);

        if(sala != null){
            return ResponseEntity.ok(sala);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<SalaDTO> crearSala(@RequestBody SalaDTO sala) {
        SalaDTO salaGuardada = salaService.crearSala(sala);
        return ResponseEntity.status(HttpStatus.CREATED).body(salaGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDTO> actualizarSala(@PathVariable Long id, @RequestBody SalaDTO datosSala) {
        try {
            SalaDTO salaActualizada = salaService.actualizarSala(id, datosSala);
            return ResponseEntity.ok(salaActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarSala(@PathVariable Long id) {
        if (salaService.getSalaById(id) != null){
            salaService.borrarSala(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
