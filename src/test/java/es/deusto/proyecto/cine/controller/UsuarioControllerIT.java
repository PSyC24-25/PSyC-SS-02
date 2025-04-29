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
@AutoConfigureMockMvc(addFilters = false)  // Desactiva seguridad para evitar 403 y 302
class UsuarioControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void obtenerTodosLosUsuarios_debeMostrarVistaCorrecta() throws Exception {
        mockMvc.perform(get("/usuarios"))
            .andExpect(status().isOk())
            .andExpect(view().name("usuarios"))  // Asegúrate de que esta vista existe
            .andExpect(model().attributeExists("usuarios"));
    }

    @Test
    void obtenerUsuarioPorId_debeMostrarVistaCorrecta() throws Exception {
        Long usuarioId = 1L;

        mockMvc.perform(get("/usuarios/{id}", usuarioId))
            .andExpect(status().isOk())
            .andExpect(view().name("detalle-usuario"))  // Cambia si tu vista tiene otro nombre
            .andExpect(model().attributeExists("usuario"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void crearUsuario_debeRedirigirACorrectaVista() throws Exception {
        String nuevoUsuarioJson = "{ \"nombre\": \"Juan Perez\", \"correo\": \"juan.perez@example.com\", \"contrasena\": \"password123\" }";

        mockMvc.perform(post("/usuarios")
                .contentType("application/json")
                .content(nuevoUsuarioJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nombre").value("Juan Perez"))
            .andExpect(jsonPath("$.correo").value("juan.perez@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void actualizarUsuario_debeDevolverUsuarioActualizado() throws Exception {
        Long usuarioId = 1L;
        String usuarioActualizadoJson = "{ \"nombre\": \"Juan Perez Actualizado\", \"correo\": \"juan.perez.updated@example.com\", \"contrasena\": \"newpassword123\" }";

        mockMvc.perform(put("/usuarios/{id}", usuarioId)
                .contentType("application/json")
                .content(usuarioActualizadoJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Juan Perez Actualizado"))
            .andExpect(jsonPath("$.correo").value("juan.perez.updated@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void borrarUsuario_debeEliminarCorrectamente() throws Exception {
        Long usuarioId = 1L;

        mockMvc.perform(delete("/usuarios/{id}", usuarioId))
            .andExpect(status().isNoContent());
    }
}
