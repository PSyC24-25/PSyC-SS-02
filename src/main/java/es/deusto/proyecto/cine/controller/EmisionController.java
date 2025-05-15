package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.dto.EmisionDTO;
import es.deusto.proyecto.cine.service.EmisionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gestionar las emisiones de películas.
 * 
 * Este controlador proporciona funcionalidades para crear,
 * actualizar, eliminar y consultar emisiones de películas.
 */
@Controller
@RequestMapping("/emisiones")
public class EmisionController {
    
    @Autowired
    private EmisionService emisionService;

    // @GetMapping
    // public List<EmisionDTO> getAllEmisiones() {
    //     return emisionService.getAllEmisiones();
    // }
    
    /**
     * Muestra la vista de emisiones.
     *
     * @param model el modelo para pasar datos a la vista
     * @return la vista de emisiones (fechas del día actual y posteriores)
     */
    @GetMapping
    public String getAllPeliculas(Model model) {
        List<EmisionDTO> emisiones = emisionService.getAllEmisiones();
        //ESTOS FILTRA LAS EMISIONES EN BASE A LAS QUE HAY DISPONIBLES A PARTIR DEL DIA DE HOY 
        //AHORA MISMO NO HAY NI UNA
        LocalDate today = LocalDate.now();

        List<EmisionDTO> emisionesHoy = emisiones.stream()
            .filter(emision -> emision.getFecha().toLocalDate().isEqual(today))
            .collect(Collectors.toList());

        List<EmisionDTO> emisionesPosteriores = emisiones.stream()
            .filter(emision -> emision.getFecha().toLocalDate().isAfter(today))
            .collect(Collectors.toList());

        model.addAttribute("emisionesHoy", emisionesHoy);
        model.addAttribute("emisionesPosteriores", emisionesPosteriores);
        model.addAttribute("emisiones", emisiones);
        return "emisiones";
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
