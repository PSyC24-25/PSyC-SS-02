package es.deusto.proyecto.cine.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.deusto.proyecto.cine.dto.CompraDTO;
import es.deusto.proyecto.cine.model.Compra;
import es.deusto.proyecto.cine.model.Emision;
import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.repository.CompraRepository;
import es.deusto.proyecto.cine.repository.EmisionRepository;
import es.deusto.proyecto.cine.repository.UsuarioRepository;

public class CompraServiceTest {
    @Mock
    private CompraRepository compraRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EmisionRepository emisionRepository;

    @InjectMocks
    private CompraService compraService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("Obtener todas las compras correctamente")
    void getAllCompras() {
        Usuario usuario1 = new Usuario();
        usuario1.setCodUsuario(1L);

        Emision emision1 = new Emision();
        emision1.setCodEmision(1L);

        Compra compra1 = new Compra();
        compra1.setCodCompra(1L);
        compra1.setUsuario(usuario1);
        compra1.setEmision(emision1);
        compra1.setAsientos(Arrays.asList("D1", "D2"));

        Usuario usuario2 = new Usuario();
        usuario2.setCodUsuario(2L);

        Emision emision2 = new Emision();
        emision2.setCodEmision(2L);

        Compra compra2 = new Compra();
        compra2.setCodCompra(2L);
        compra2.setUsuario(usuario2);
        compra2.setEmision(emision2);
        compra2.setAsientos(Arrays.asList("E1", "E2"));

        when(compraRepository.findAll()).thenReturn(Arrays.asList(compra1, compra2));

        List<CompraDTO> compras = compraService.getAllCompras();

        assertNotNull(compras);
        assertEquals(2, compras.size());
        assertEquals(1L, compras.get(0).getCodCompra());
        assertEquals(2L, compras.get(1).getCodCompra());
    }

    @Test
    @DisplayName("Obtener compra por ID correctamente")
    void getCompraById() {
        Usuario usuario = new Usuario();
        usuario.setCodUsuario(1L);

        Emision emision = new Emision();
        emision.setCodEmision(1L);

        Compra compra = new Compra();
        compra.setCodCompra(1L);
        compra.setUsuario(usuario);
        compra.setEmision(emision);
        compra.setAsientos(Arrays.asList("C1", "C2"));

        when(compraRepository.findById(1L)).thenReturn(Optional.of(compra));

        CompraDTO compraDTO = compraService.getCompraById(1L);

        assertNotNull(compraDTO);
        assertEquals(1L, compraDTO.getCodCompra());
        assertEquals(1L, compraDTO.getIdUsuario());
        assertEquals(1L, compraDTO.getIdEmision());
        assertEquals(Arrays.asList("C1", "C2"), compraDTO.getAsientos());
    }

    @Test
    @DisplayName("Crear compra correctamente")
    void crearCompra() {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setCodCompra(1L);
        compraDTO.setIdUsuario(1L);
        compraDTO.setIdEmision(1L);
        compraDTO.setAsientos(Arrays.asList("A1", "A2"));

        Usuario usuario = new Usuario();
        usuario.setCodUsuario(1L);

        Emision emision = new Emision();
        emision.setCodEmision(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(emisionRepository.findByCodEmision(1L)).thenReturn(emision);
        when(compraRepository.save(any(Compra.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CompraDTO result = compraService.crearCompra(compraDTO);

        assertNotNull(result);
        assertEquals(1L, result.getCodCompra());
        assertEquals(1L, result.getIdUsuario());
        assertEquals(1L, result.getIdEmision());
        assertEquals(Arrays.asList("A1", "A2"), result.getAsientos());
    }

    @Test
    @DisplayName("Actualizar compra correctamente")
    void actualizarCompra() {
        Compra compra = new Compra();
        compra.setCodCompra(1L);

        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setCodCompra(1L);
        compraDTO.setIdUsuario(1L);
        compraDTO.setIdEmision(1L);
        compraDTO.setAsientos(Arrays.asList("B1", "B2"));

        Usuario usuario = new Usuario();
        usuario.setCodUsuario(1L);

        Emision emision = new Emision();
        emision.setCodEmision(1L);

        when(compraRepository.findById(1L)).thenReturn(Optional.of(compra));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(emisionRepository.findById(1L)).thenReturn(Optional.of(emision));
        when(compraRepository.save(any(Compra.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CompraDTO result = compraService.actualizarCompra(1L, compraDTO);

        assertNotNull(result);
        assertEquals(1L, result.getCodCompra());
        assertEquals(1L, result.getIdUsuario());
        assertEquals(1L, result.getIdEmision());
        assertEquals(Arrays.asList("B1", "B2"), result.getAsientos());
    }

    @Test
    @DisplayName("Borrar compra correctamente")
    void borrarCompra() {
        when(compraRepository.existsById(1L)).thenReturn(true);
        doNothing().when(compraRepository).deleteById(1L);

        assertDoesNotThrow(() -> compraService.borrarCompra(1L));

        verify(compraRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Borrar compra - Compra no encontrada")
    void borrarCompraNoEncontrada() {
        when(compraRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            compraService.borrarCompra(1L);
        });

        assertEquals("No existe compra con id: 1", exception.getMessage());
    }
}
