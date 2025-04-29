import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.repository.UsuarioRepository;
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
class IndiceControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @WithMockUser(username = "usuario@example.com", roles = {"USER"})
    void accesoALaPaginaDeInicio_conUsuarioAutenticado_debeMostrarVistaCorrecta() throws Exception {
        // Primero, asegúrate de que hay un usuario con el correo que se va a utilizar
        String email = "usuario@example.com";
        if (usuarioRepository.findByCorreo(email).isEmpty()) {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setCorreo(email);
            nuevoUsuario.setNombre("Nombre de Usuario");
            usuarioRepository.save(nuevoUsuario);
        }

        mockMvc.perform(get("/"))
            .andExpect(status().isOk())  // Verifica que la respuesta sea 200 OK
            .andExpect(view().name("index"))  // Verifica que la vista es la correcta (index)
            .andExpect(model().attributeExists("usuario"))  // Verifica que el modelo contiene el atributo 'usuario'
            .andExpect(model().attribute("usuario", usuarioRepository.findByCorreo(email).get()));  // Verifica que el usuario en el modelo es el esperado
    }

    @Test
    void accesoALaPaginaDeInicio_sinUsuarioAutenticado_debeMostrarVistaCorrecta() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())  // Verifica que la respuesta sea 200 OK
            .andExpect(view().name("index"))  // Verifica que la vista es la correcta (index)
            .andExpect(model().attributeDoesNotExist("usuario"));  // Verifica que no se pasa el atributo 'usuario' al modelo
    }
}
