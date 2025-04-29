package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.dto.EmisionDTO;
import es.deusto.proyecto.cine.service.EmisionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
