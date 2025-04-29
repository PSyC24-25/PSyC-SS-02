package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.dto.CompraDTO;
import es.deusto.proyecto.cine.dto.EmisionDTO;
import es.deusto.proyecto.cine.model.Sala;
import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.service.CompraService;
import es.deusto.proyecto.cine.service.EmisionService;
import es.deusto.proyecto.cine.service.SalaService;
import es.deusto.proyecto.cine.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/compras")
public class CompraController {
    
    private final CompraService compraService;
    private final EmisionService emisionService;
    private final UsuarioService usuarioService;
    private final SalaService salaService;

    @Autowired
    public CompraController(CompraService compraService, EmisionService emisionService, 
                            UsuarioService usuarioService, SalaService salaService) {
        this.compraService = compraService;
        this.emisionService = emisionService;
        this.usuarioService = usuarioService;
        this.salaService = salaService;
    }

    @GetMapping
    public String seleccionarEmision(Model model) {
        LocalDate today = LocalDate.now();

        List<EmisionDTO> emisiones = emisionService.getAllEmisiones().stream()
            .filter(emision -> !emision.getFecha().toLocalDate().isBefore(today))
            .toList();

        model.addAttribute("emisiones", emisiones);
        return "seleccionar_emision";
    }

    @GetMapping("/crear")
    public String getAllCompras(@RequestParam("emisionId") Long emisionId, Model model, Authentication authentication) {
        EmisionDTO emisionDTO = emisionService.getEmisionById(emisionId);
        int numSala = emisionDTO.getNumSala();
        Sala sala = salaService.getSalaByNumero(numSala);

        List<String> asientosOcupados = compraService.getAsientosOcupadosPorEmision(emisionService.ConvertirAEntidad(emisionDTO));
        
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setIdEmision(emisionId);
        

        if (authentication != null) {
            Usuario usuario = usuarioService.obtenerUsuarioPorEmail(authentication.getName());
            compraDTO.setIdUsuario(usuario.getCodUsuario());
            model.addAttribute("usuario", usuario);
        }
        int filas = (int) Math.ceil((double) sala.getCapacidad() / sala.getColumnas());

        model.addAttribute("filas", filas);
        model.addAttribute("nuevaCompra", compraDTO);
        model.addAttribute("emision", emisionDTO);
        model.addAttribute("asientosOcupados", asientosOcupados);
        model.addAttribute("sala", sala);
        return "compra_boletos";
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraDTO> getCompraById(@PathVariable Long id) {
        CompraDTO compra = compraService.getCompraById(id);

        if(compra != null){
            return ResponseEntity.ok(compra);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public String crearCompra(@ModelAttribute CompraDTO compraDTO, Authentication authentication) {
        if (authentication != null && compraDTO.getIdUsuario() == null) {
            Usuario usuario = usuarioService.obtenerUsuarioPorEmail(authentication.getName());
            compraDTO.setIdUsuario(usuario.getCodUsuario());
        }
        compraDTO.setAsientos(compraDTO.getAsientos());
        compraService.crearCompra(compraDTO);
        return "redirect:/compras";
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
