package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // Desactiva seguridad para simplificar
class IndiceControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final String correoTest = "usuario@example.com";

    @BeforeEach
    void setupUsuario() {
        // Asegurarse de que el usuario exista antes de los tests
        if (usuarioRepository.findByCorreo(correoTest).isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setNombre("Usuario de Prueba");
            usuario.setCorreo(correoTest);
            usuario.setContrasenya("1234");
            usuarioRepository.save(usuario);
        }
    }

    @Test
    @WithMockUser(username = correoTest, roles = {"USER"})
    void accesoALaPaginaDeInicio_conUsuarioAutenticado_debeMostrarVistaCorrecta() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"))
            .andExpect(model().attributeExists("usuario")); // Solo verifica existencia, no entidad
    }

    @Test
    void accesoALaPaginaDeInicio_sinUsuarioAutenticado_debeMostrarVistaCorrecta() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"))
            .andExpect(model().attributeDoesNotExist("usuario"));
    }
}
