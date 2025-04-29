package es.deusto.proyecto.cine.controller;

import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AutenticacionController.class)
@AutoConfigureMockMvc(addFilters = false)  // desactiva seguridad en los tests
class AutenticacionControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void login_debeDevolverVistaLogin() throws Exception {
        mockMvc.perform(get("/autenticacion/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("login"));
    }

    @Test
    void formularioRegistro_debeMostrarVistaRegistroConDTO() throws Exception {
        mockMvc.perform(get("/autenticacion/registro"))
            .andExpect(status().isOk())
            .andExpect(view().name("registro"))
            .andExpect(model().attributeExists("usuario"));
    }

    @Test
    void registrarUsuario_debeLlamarAlServicioYRedirigir() throws Exception {
        mockMvc.perform(post("/autenticacion/registro")
                .param("correo", "test@correo.com")
                .param("contrasena", "1234"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/autenticacion/login"));

        verify(usuarioService).crearUsuario(org.mockito.ArgumentMatchers.any(Usuario.class));
    }
}
