package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.dto.CompraDTO;
import es.deusto.proyecto.cine.service.CompraService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/compras")
public class CompraController {
    
    @Autowired
    private CompraService compraService;

    @GetMapping
    public List<CompraDTO> getAllCompras() {
        return compraService.getAllCompras();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraDTO> getCompraById(@PathVariable Long id) {
        CompraDTO compra = compraService.getCompraById(id);

        if(compra != null){
            return ResponseEntity.ok(compra);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CompraDTO> crearCompra(@RequestBody CompraDTO compraDTO) {
        CompraDTO compraGuardada = compraService.crearCompra(compraDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(compraGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompraDTO> actualizarCompra(@PathVariable Long id, @RequestBody CompraDTO datosCompra) {
        try {
            CompraDTO compraActualizada = compraService.actualizarCompra(id, datosCompra);
            return ResponseEntity.ok(compraActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarCompra(@PathVariable Long id) {
        if (compraService.getCompraById(id) != null){
            compraService.borrarCompra(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
