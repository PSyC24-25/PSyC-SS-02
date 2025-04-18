package es.deusto.proyecto.cine.service;

import es.deusto.proyecto.cine.dto.CompraDTO;
import es.deusto.proyecto.cine.model.Compra;
import es.deusto.proyecto.cine.model.Emision;
import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.repository.CompraRepository;
import es.deusto.proyecto.cine.repository.EmisionRepository;
import es.deusto.proyecto.cine.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompraService {
    
    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private EmisionRepository emisionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public double calcPrecio(CompraDTO compraDTO){
        double precioPorAsiento = 9.5;
        return precioPorAsiento * compraDTO.getAsientos().size(); 
    }

    private CompraDTO convertirADTO(Compra compra){
        return new CompraDTO(compra.getCodCompra(), compra.getUsuario().getCodUsuario(), compra.getEmision().getCodEmision()
        , compra.getAsientos());
    }

    private Compra ConvertirAEntidad(CompraDTO compraDTO){
        Emision emision = emisionRepository.findByCodEmision(compraDTO.getIdEmision());
        Usuario usuario = usuarioRepository.findById(compraDTO.getIdUsuario())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Compra compra = new Compra();
        compra.setCodCompra(compraDTO.getCodCompra());
        compra.setEmision(emision);
        compra.setUsuario(usuario);
        compra.setPrecio(calcPrecio(compraDTO));
        compra.setAsientos(compraDTO.getAsientos());

        return compra;
    }

    public List<String> getAsientosOcupadosPorEmision(Emision emision) {
        List<Compra> compras = compraRepository.findByEmision(emision);
        
        List<String> ocupados = new ArrayList<>();
        if (compras != null){
            for (Compra compra : compras) {
                ocupados.addAll(compra.getAsientos());
            }
        }
        return ocupados;
    }

    public List<CompraDTO> getAllCompras() {
        return compraRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public CompraDTO getCompraById(Long id) {
        return compraRepository.findById(id)
        .map(this::convertirADTO) 
        .orElse(null);  
    }

    public CompraDTO crearCompra(CompraDTO compraDTO){
        Compra compra = ConvertirAEntidad(compraDTO);
        Compra compraGuardada = compraRepository.save(compra);
        return convertirADTO(compraGuardada);
    }

    public CompraDTO actualizarCompra(Long id, CompraDTO actualizarCompra) {
        Optional<Compra> compraExistente = compraRepository.findById(id);

        if (compraExistente.isPresent()) {
            Compra compra = compraExistente.get();

            Usuario usuario = usuarioRepository.findById(actualizarCompra.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            compra.setUsuario(usuario);

            compra.setAsientos(actualizarCompra.getAsientos());

            Emision emision = emisionRepository.findById(actualizarCompra.getIdEmision())
                .orElseThrow(() -> new RuntimeException("Emision no encontrada"));
            compra.setEmision(emision);

            compra.setPrecio(calcPrecio(actualizarCompra));

            Compra compraActualizada = compraRepository.save(compra);
            return convertirADTO(compraActualizada);
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
