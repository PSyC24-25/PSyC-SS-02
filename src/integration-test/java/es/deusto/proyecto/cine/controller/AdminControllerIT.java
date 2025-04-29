import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void accesoAlPanelAdmin_debeMostrarVistaCorrecta() throws Exception {
        mockMvc.perform(get("/admin"))
            .andExpect(status().isOk())
            .andExpect(view().name("panel_admin"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void verPeliculasAdmin_debeMostrarVistaConPeliculas() throws Exception {
        mockMvc.perform(get("/admin/peliculas"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin_peliculas"))
            .andExpect(model().attributeExists("peliculas"))
            .andExpect(model().attributeExists("nuevaPelicula"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void verEmisionesAdmin_debeMostrarVistaConDatos() throws Exception {
        mockMvc.perform(get("/admin/emisiones"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin_emisiones"))
            .andExpect(model().attributeExists("nuevaEmision"))
            .andExpect(model().attributeExists("peliculas"))
            .andExpect(model().attributeExists("salas"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void verUsuariosAdmin_debeMostrarVistaConUsuarios() throws Exception {
        mockMvc.perform(get("/admin/usuarios"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin_usuarios"))
            .andExpect(model().attributeExists("usuarios"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void verSalasAdmin_debeMostrarVistaConSalas() throws Exception {
        mockMvc.perform(get("/admin/salas"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin_salas"))
            .andExpect(model().attributeExists("salas"))
            .andExpect(model().attributeExists("nuevaSala"));
    }
}
