package es.deusto.proyecto.cine.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)  // ❌ Desactiva seguridad para evitar 403 y 302
class SalaControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void obtenerTodasLasSalas_debeMostrarVistaCorrecta() throws Exception {
        mockMvc.perform(get("/salas"))
            .andExpect(status().isOk())
            .andExpect(view().name("salas"))  // Asegúrate de que tu controlador devuelve esta vista
            .andExpect(model().attributeExists("salas"));
    }

    @Test
    void obtenerSalaPorId_debeMostrarVistaCorrecta() throws Exception {
        Long salaId = 1L;

        mockMvc.perform(get("/salas/{id}", salaId))
            .andExpect(status().isOk())
            .andExpect(view().name("detalle-sala"))  // Ajusta esto al nombre real de tu vista
            .andExpect(model().attributeExists("sala"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")  // ✅ Necesario si el endpoint está protegido
    void borrarSala_debeEliminarCorrectamente() throws Exception {
        Long salaId = 1L;

        mockMvc.perform(delete("/salas/{id}", salaId))
            .andExpect(status().isNoContent());
    }
}
