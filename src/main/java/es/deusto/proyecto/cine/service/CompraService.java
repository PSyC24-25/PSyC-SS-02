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

/**
 * Servicio para gestionar las compras del sistema.
 * 
 * Este servicio proporciona funcionalidades para crear,
 * actualizar, eliminar y consultar compras. Además,
 * permite calcular el precio de una compra y obtener
 * los asientos ocupados por una emisión.
 */
@Service
public class CompraService {
    
    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private EmisionRepository emisionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * calcula el precio de una compra en base al número de asientos.
     *
     * @param compraDTO objeto DTO de la compra
     * @return el precio de la compra
     */
    public double calcPrecio(CompraDTO compraDTO){
        double precioPorAsiento = 9.5;
        return precioPorAsiento * compraDTO.getAsientos().size(); 
    }

    /**
     * Convierte una entidad Compra a un objeto DTO.
     *
     * @param compra objeto de la compra
     * @return el objeto DTO de la compra
     */
    private CompraDTO convertirADTO(Compra compra){
        if (compra.getUsuario() == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        if (compra.getEmision() == null) {
            throw new RuntimeException("Emision no encontrada");
        }
        return new CompraDTO(compra.getCodCompra(), compra.getUsuario().getCodUsuario(), compra.getEmision().getCodEmision()
        , compra.getAsientos());
    }

    /**
     * Convierte un objeto DTO de compra a una entidad Compra.
     *
     * @param compraDTO objeto DTO de la compra
     * @return la entidad Compra
     */
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

    /**
     * Obtiene una lista de asientos ocupados para una emisión específica.
     *
     * @param emision la emisión para la que se desean obtener los asientos ocupados
     * @return una lista de asientos ocupados
     */
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

    /**
     * Obtiene una lista de todas las compras.
     *
     * @return una lista de objetos DTO de compras
     */
    public List<CompraDTO> getAllCompras() {
        return compraRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una compra por su ID.
     *
     * @param id el ID de la compra
     * @return el objeto DTO de la compra, o null si no se encuentra
     */
    public CompraDTO getCompraById(Long id) {
        return compraRepository.findById(id)
        .map(this::convertirADTO) 
        .orElse(null);  
    }

    /**
     * Guarda una compra en la base de datos.
     *
     * @param compra la entidad Compra a guardar
     */
    public CompraDTO crearCompra(CompraDTO compraDTO){
        Compra compra = ConvertirAEntidad(compraDTO);
        Compra compraGuardada = compraRepository.save(compra);
        return convertirADTO(compraGuardada);
    }

    /**
     * Actualiza una compra existente.
     *
     * @param id el ID de la compra a actualizar
     * @param actualizarCompra el objeto DTO con los nuevos datos
     * @return el objeto DTO de la compra actualizada
     */
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

    /**
     * Elimina una compra por su ID.
     *
     * @param id el ID de la compra a eliminar
     */
    public void borrarCompra(Long id){
        if (compraRepository.existsById(id)) {
            compraRepository.deleteById(id);
        } else {
            throw new RuntimeException("No existe compra con id: " + id);
        }
    }

    public List<CompraDTO> getComprasPorUsuario(Long usuarioId) {
    List<Compra> compras = compraRepository.findByUsuarioCodUsuario(usuarioId);
    return compras.stream()
                  .map(this::convertirADTO)
                  .collect(Collectors.toList());
    }

    public void eliminarCompra(Long codCompra) {
        compraRepository.deleteById(codCompra);
    }
}
