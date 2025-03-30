package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.dto.EmisionDTO;
import es.deusto.proyecto.cine.service.EmisionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emisiones")
public class EmisionController {
    
    @Autowired
    private EmisionService emisionService;

    @GetMapping
    public List<EmisionDTO> getAllEmisiones() {
        return emisionService.getAllEmisiones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmisionDTO> getEmisionById(@PathVariable Long id) {
        EmisionDTO emision = emisionService.getEmisionById(id);

        if(emision != null){
            return ResponseEntity.ok(emision);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EmisionDTO> crearEmision(@RequestBody EmisionDTO emisionDTO) {
        EmisionDTO emisionGuardada = emisionService.crearEmision(emisionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(emisionGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmisionDTO> actualizarEmision(@PathVariable Long id, @RequestBody EmisionDTO datosEmisionDTO) {
        try {
            EmisionDTO emisionActualizada = emisionService.actualizarEmision(id, datosEmisionDTO);
            return ResponseEntity.ok(emisionActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarEmision(@PathVariable Long id) {
        if (emisionService.getEmisionById(id) != null){
            emisionService.borrarEmision(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
