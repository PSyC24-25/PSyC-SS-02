package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.dto.EmisionDTO;
import es.deusto.proyecto.cine.service.EmisionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmisionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmisionService emisionService;

    @InjectMocks
    private EmisionController emisionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(emisionController).build();
    }

    @Test
    void testGetAllPeliculas() throws Exception {
        EmisionDTO emision1 = new EmisionDTO();
        emision1.setFecha(LocalDate.now());
        EmisionDTO emision2 = new EmisionDTO();
        emision2.setFecha(LocalDate.now().plusDays(1));

        List<EmisionDTO> emisiones = List.of(emision1, emision2);
        when(emisionService.getAllEmisiones()).thenReturn(emisiones);

        MvcResult result = mockMvc.perform(get("/emisiones"))
                .andExpect(status().isOk())
                .andExpect(view().name("emisiones"))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        // Add any further assertions if necessary
    }

    @Test
    void testGetEmisionById() throws Exception {
        EmisionDTO emisionDTO = new EmisionDTO();
        emisionDTO.setFecha(LocalDate.now());

        when(emisionService.getEmisionById(anyLong())).thenReturn(emisionDTO);

        mockMvc.perform(get("/emisiones/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fecha").value(emisionDTO.getFecha().toString()))
                .andReturn();
    }

    @Test
    void testCrearEmision() throws Exception {
        EmisionDTO emisionDTO = new EmisionDTO();
        emisionDTO.setFecha(LocalDate.now());

        when(emisionService.crearEmision(any(EmisionDTO.class))).thenReturn(emisionDTO);

        mockMvc.perform(post("/emisiones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fecha\":\"" + LocalDate.now() + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fecha").value(emisionDTO.getFecha().toString()))
                .andReturn();
    }

    @Test
    void testActualizarEmision() throws Exception {
        EmisionDTO emisionDTO = new EmisionDTO();
        emisionDTO.setFecha(LocalDate.now());

        when(emisionService.actualizarEmision(anyLong(), any(EmisionDTO.class))).thenReturn(emisionDTO);

        mockMvc.perform(put("/emisiones/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fecha\":\"" + LocalDate.now() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fecha").value(emisionDTO.getFecha().toString()))
                .andReturn();
    }

    @Test
    void testBorrarEmision() throws Exception {
        EmisionDTO emisionDTO = new EmisionDTO();
        when(emisionService.getEmisionById(anyLong())).thenReturn(emisionDTO);
        doNothing().when(emisionService).borrarEmision(anyLong());

        mockMvc.perform(delete("/emisiones/{id}", 1L))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void testBorrarEmisionNotFound() throws Exception {
        when(emisionService.getEmisionById(anyLong())).thenReturn(null);

        mockMvc.perform(delete("/emisiones/{id}", 1L))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
