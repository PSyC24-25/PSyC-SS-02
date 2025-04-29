import es.deusto.proyecto.cine.dto.SalaDTO;
import es.deusto.proyecto.cine.service.SalaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SalaControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SalaService salaService;

    @Test
    void obtenerTodasLasSalas_debeMostrarListaCorrecta() throws Exception {
        mockMvc.perform(get("/salas"))
            .andExpect(status().isOk())  // Verifica que la respuesta es 200 OK
            .andExpect(jsonPath("$").isArray())  // Verifica que la respuesta es un array
            .andExpect(jsonPath("$[0].id").exists())  // Verifica que la primera sala tiene un campo id
            .andExpect(jsonPath("$[0].nombre").exists());  // Verifica que la primera sala tiene un campo nombre
    }

    @Test
    void obtenerSalaPorId_debeMostrarSalaCorrecta() throws Exception {
        Long salaId = 1L;  // Asegúrate de que esta sala existe en la base de datos

        mockMvc.perform(get("/salas/{id}", salaId))
            .andExpect(status().isOk())  // Verifica que la respuesta es 200 OK
            .andExpect(jsonPath("$.id").value(salaId))  // Verifica que el ID de la sala es correcto
            .andExpect(jsonPath("$.nombre").exists())  // Verifica que el nombre de la sala existe
            .andExpect(jsonPath("$.capacidad").exists());  // Verifica que la capacidad de la sala existe
    }

    @Test
    void crearSala_debeRedirigirACorrectaVista() throws Exception {
        SalaDTO nuevaSala = new SalaDTO();
        nuevaSala.setNombre("Sala Test");
        nuevaSala.setCapacidad(200);

        mockMvc.perform(post("/salas")
                .contentType("application/json")
                .content("{\"nombre\":\"Sala Test\", \"capacidad\":200}"))
            .andExpect(status().isCreated())  // Verifica que la respuesta es 201 Created
            .andExpect(jsonPath("$.nombre").value("Sala Test"))  // Verifica que el nombre de la sala es correcto
            .andExpect(jsonPath("$.capacidad").value(200));  // Verifica que la capacidad de la sala es correcta
    }

    @Test
    void actualizarSala_debeDevolverSalaActualizada() throws Exception {
        Long salaId = 1L;  // Asegúrate de que esta sala existe en la base de datos
        SalaDTO salaActualizada = new SalaDTO();
        salaActualizada.setNombre("Sala Actualizada");
        salaActualizada.setCapacidad(250);

        mockMvc.perform(put("/salas/{id}", salaId)
                .contentType("application/json")
                .content("{\"nombre\":\"Sala Actualizada\", \"capacidad\":250}"))
            .andExpect(status().isOk())  // Verifica que la respuesta es 200 OK
            .andExpect(jsonPath("$.nombre").value("Sala Actualizada"))  // Verifica que el nombre es actualizado
            .andExpect(jsonPath("$.capacidad").value(250));  // Verifica que la capacidad es actualizada
    }

    @Test
    void borrarSala_debeEliminarCorrectamente() throws Exception {
        Long salaId = 1L;  // Asegúrate de que esta sala existe en la base de datos

        mockMvc.perform(delete("/salas/{id}", salaId))
            .andExpect(status().isNoContent());  // Verifica que la respuesta es 204 No Content
    }
}
