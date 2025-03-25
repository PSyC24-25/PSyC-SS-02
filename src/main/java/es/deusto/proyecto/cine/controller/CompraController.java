package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.model.Compra;
import es.deusto.proyecto.cine.service.CompraService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CompraController {
    
    @Autowired
    private CompraService compraService;

    @GetMapping("/compras")
    public List<Compra> getAllCompras() {
        return compraService.getAllCompras();
    }

    @GetMapping("/compras/{id}")
    public ResponseEntity<Compra> getCompraById(@PathVariable Long id) {
        Optional<Compra> compra = compraService.getCompraById(id);

        if(compra.isPresent()){
            return ResponseEntity.ok(compra.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Compra crearCompra(@RequestBody Compra compra) {
        return compraService.crearCompra(compra);
    }

    @PutMapping("/compras/{id}")
    public ResponseEntity<Compra> actualizarCompra(@PathVariable Long id, @RequestBody Compra datosCompra) {
        try {
            Compra compraActualizada = compraService.actualizarCompra(id, datosCompra);
            return ResponseEntity.ok(compraActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/compras/{id}")
    public ResponseEntity<Void> borrarCompra(@PathVariable Long id) {
        if (compraService.getCompraById(id).isPresent()){
            compraService.borrarCompra(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
