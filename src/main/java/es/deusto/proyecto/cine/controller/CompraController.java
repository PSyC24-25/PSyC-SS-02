package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.model.Compra;
import es.deusto.proyecto.cine.service.CompraService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompraController {
    
    @Autowired
    private CompraService compraService;

    @GetMapping("/compras")
    public List<Compra> getAllCompras() {
        return compraService.getAllCompras();
    }

    @GetMapping("/compras/{id}")
    public Compra getCompraById(@PathVariable Long id) {
        return compraService.getCompraById(id);
    }
}
