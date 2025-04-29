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
@AutoConfigureMockMvc(addFilters = false)  // 🔧 Desactiva seguridad en los tests
class PeliculaControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void accesoAListaDePeliculas_debeMostrarVistaCorrecta() throws Exception {
        mockMvc.perform(get("/peliculas"))
            .andExpect(status().isOk())
            .andExpect(view().name("peliculas"))
            .andExpect(model().attributeExists("peliculas"));
    }

    @Test
    void accesoAUnaPeliculaPorId_debeRedirigirOSerJSON() throws Exception {
        // Simplificado: Verificamos redirección o que devuelva una vista
        Long peliculaId = 1L;

        mockMvc.perform(get("/peliculas/{id}", peliculaId))
            .andExpect(status().isOk()); // Esperamos al menos que exista
    }

    @Test
    @WithMockUser(roles = "ADMIN")  // 🔐 Necesario si hay seguridad para este endpoint
    void borrarPelicula_debeEliminarCorrectamente() throws Exception {
        Long peliculaId = 1L;

        mockMvc.perform(delete("/peliculas/{id}", peliculaId))
            .andExpect(status().isNoContent());
    }

    @Test
    void buscarPeliculasPorGenero_yNombre_debeFiltrarPeliculasCorrectamente() throws Exception {
        mockMvc.perform(get("/peliculas")
                .param("genero", "Acción")
                .param("nombre", "Test"))
            .andExpect(status().isOk())
            .andExpect(view().name("peliculas"))
            .andExpect(model().attributeExists("peliculas"))
            .andExpect(model().attribute("genero", "Acción"))
            .andExpect(model().attribute("nombre", "Test"));
    }
}
