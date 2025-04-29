import es.deusto.proyecto.cine.dto.UsuarioDTO;
import es.deusto.proyecto.cine.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioService usuarioService;

    @Test
    void obtenerTodosLosUsuarios_debeMostrarListaCorrecta() throws Exception {
        mockMvc.perform(get("/usuarios"))
            .andExpect(status().isOk())  // Verifica que la respuesta es 200 OK
            .andExpect(jsonPath("$").isArray())  // Verifica que la respuesta es un array
            .andExpect(jsonPath("$[0].id").exists())  // Verifica que el primer usuario tiene un campo id
            .andExpect(jsonPath("$[0].nombre").exists());  // Verifica que el primer usuario tiene un campo nombre
    }

    @Test
    void obtenerUsuarioPorId_debeMostrarUsuarioCorrecto() throws Exception {
        Long usuarioId = 1L;  // Asegúrate de que este usuario existe en la base de datos

        mockMvc.perform(get("/usuarios/{id}", usuarioId))
            .andExpect(status().isOk())  // Verifica que la respuesta es 200 OK
            .andExpect(jsonPath("$.id").value(usuarioId))  // Verifica que el ID del usuario es correcto
            .andExpect(jsonPath("$.nombre").exists())  // Verifica que el nombre del usuario existe
            .andExpect(jsonPath("$.correo").exists());  // Verifica que el correo del usuario existe
    }

    @Test
    void crearUsuario_debeRedirigirACorrectaVista() throws Exception {
        String nuevoUsuarioJson = "{ \"nombre\": \"Juan Perez\", \"correo\": \"juan.perez@example.com\", \"contrasena\": \"password123\" }";

        mockMvc.perform(post("/usuarios")
                .contentType("application/json")
                .content(nuevoUsuarioJson))
            .andExpect(status().isCreated())  // Verifica que la respuesta es 201 Created
            .andExpect(jsonPath("$.nombre").value("Juan Perez"))  // Verifica que el nombre del usuario es correcto
            .andExpect(jsonPath("$.correo").value("juan.perez@example.com"));  // Verifica que el correo del usuario es correcto
    }

    @Test
    void actualizarUsuario_debeDevolverUsuarioActualizado() throws Exception {
        Long usuarioId = 1L;  // Asegúrate de que este usuario existe en la base de datos
        String usuarioActualizadoJson = "{ \"nombre\": \"Juan Perez Actualizado\", \"correo\": \"juan.perez.updated@example.com\", \"contrasena\": \"newpassword123\" }";

        mockMvc.perform(put("/usuarios/{id}", usuarioId)
                .contentType("application/json")
                .content(usuarioActualizadoJson))
            .andExpect(status().isOk())  // Verifica que la respuesta es 200 OK
            .andExpect(jsonPath("$.nombre").value("Juan Perez Actualizado"))  // Verifica que el nombre del usuario se actualizó
            .andExpect(jsonPath("$.correo").value("juan.perez.updated@example.com"));  // Verifica que el correo del usuario se actualizó
    }

    @Test
    void borrarUsuario_debeEliminarCorrectamente() throws Exception {
        Long usuarioId = 1L;  // Asegúrate de que este usuario existe en la base de datos

        mockMvc.perform(delete("/usuarios/{id}", usuarioId))
            .andExpect(status().isNoContent());  // Verifica que la respuesta es 204 No Content
    }
}
