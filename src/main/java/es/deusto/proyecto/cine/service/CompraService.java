package es.deusto.proyecto.cine.service;

import es.deusto.proyecto.cine.model.Compra;
import es.deusto.proyecto.cine.repository.CompraRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompraService {
    
    @Autowired
    private CompraRepository compraRepository;

    public List<Compra> getAllCompras() {
        return compraRepository.findAll();
    }

    public Optional<Compra> getCompraById(Long id) {
        return compraRepository.findById(id);
    }

    public Compra crearCompra(Compra compra){
        return compraRepository.save(compra);
    }

    public Compra actualizarCompra(Long id, Compra actualizarCompra) {
        Optional<Compra> compraExistente = compraRepository.findById(id);
        if (compraExistente.isPresent()) {
            Compra compra = compraExistente.get();
            compra.setUsuario(actualizarCompra.getUsuario());
            compra.setAsientos(actualizarCompra.getAsientos());
            compra.setEmision(actualizarCompra.getEmision());
            compra.setPrecio(actualizarCompra.getPrecio());
            return compraRepository.save(compra);
        }
        throw new EntityNotFoundException("No se ha encontrado compra con ID " + id);
    }

    public void borrarCompra(Long id){
        if (compraRepository.existsById(id)) {
            compraRepository.deleteById(id);
        } else {
            throw new RuntimeException("No existe compra con id: " + id);
        }
    }
}
