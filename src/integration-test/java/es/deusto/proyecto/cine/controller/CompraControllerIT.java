package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.dto.CompraDTO;
import es.deusto.proyecto.cine.dto.EmisionDTO;
import es.deusto.proyecto.cine.service.CompraService;
import es.deusto.proyecto.cine.service.EmisionService;
import es.deusto.proyecto.cine.service.SalaService;
import es.deusto.proyecto.cine.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CompraControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CompraService compraService;

    @Mock
    private EmisionService emisionService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private SalaService salaService;

    @InjectMocks
    private CompraController compraController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(compraController).build();
    }

    @Test
    void testSeleccionarEmision() throws Exception {
        // Mocking the services
        when(emisionService.getAllEmisiones()).thenReturn(List.of(new EmisionDTO()));

        MvcResult result = mockMvc.perform(get("/compras"))
                .andExpect(status().isOk())
                .andExpect(view().name("seleccionar_emision"))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        // Add any further assertions if necessary
    }

    @Test
    void testGetAllCompras() throws Exception {
        // Mocking required objects
        EmisionDTO emisionDTO = new EmisionDTO();
        when(emisionService.getEmisionById(anyLong())).thenReturn(emisionDTO);
        when(salaService.getSalaByNumero(anyInt())).thenReturn(new Sala());
        when(compraService.getAsientosOcupadosPorEmision(any())).thenReturn(List.of("A1", "B2"));
        when(usuarioService.obtenerUsuarioPorEmail(anyString())).thenReturn(new Usuario());

        MvcResult result = mockMvc.perform(get("/compras/crear")
                        .param("emisionId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("compra_boletos"))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        // Add any further assertions if necessary
    }

    @Test
    void testGetCompraById() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        when(compraService.getCompraById(anyLong())).thenReturn(compraDTO);

        mockMvc.perform(get("/compras/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(compraDTO.getId()))
                .andReturn();
    }

    @Test
    void testCrearCompra() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setIdUsuario(1L);
        compraDTO.setAsientos(List.of("A1", "B2"));
        
        // Mocking the necessary services
        when(compraService.crearCompra(any())).thenReturn(compraDTO);

        mockMvc.perform(post("/compras/guardar")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("idUsuario", "1")
                        .param("asientos", "A1,B2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/compras"));
    }

    @Test
    void testActualizarCompra() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setId(1L);
        
        when(compraService.actualizarCompra(anyLong(), any())).thenReturn(compraDTO);

        mockMvc.perform(put("/compras/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"asientos\":[\"A1\",\"B2\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andReturn();
    }

    @Test
    void testBorrarCompra() throws Exception {
        when(compraService.getCompraById(anyLong())).thenReturn(new CompraDTO());
        doNothing().when(compraService).borrarCompra(anyLong());

        mockMvc.perform(delete("/compras/{id}", 1L))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void testBorrarCompraNotFound() throws Exception {
        when(compraService.getCompraById(anyLong())).thenReturn(null);

        mockMvc.perform(delete("/compras/{id}", 1L))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
