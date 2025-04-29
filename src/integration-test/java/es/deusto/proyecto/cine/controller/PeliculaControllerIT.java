import es.deusto.proyecto.cine.dto.PeliculaDTO;
import es.deusto.proyecto.cine.service.PeliculaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PeliculaControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PeliculaService peliculaService;

    @Test
    void accesoAListaDePeliculas_debeMostrarVistaCorrecta() throws Exception {
        mockMvc.perform(get("/peliculas"))
            .andExpect(status().isOk())  // Verifica que la respuesta sea 200 OK
            .andExpect(view().name("peliculas"))  // Verifica que la vista sea 'peliculas'
            .andExpect(model().attributeExists("peliculas"));  // Verifica que el modelo contiene las películas
    }

    @Test
    void accesoAUnaPeliculaPorId_debeDevolverPeliculaCorrecta() throws Exception {
        // Asume que existe una película con ID 1 en la base de datos
        Long peliculaId = 1L;

        mockMvc.perform(get("/peliculas/{id}", peliculaId))
            .andExpect(status().isOk())  // Verifica que la respuesta es 200 OK
            .andExpect(jsonPath("$.id").value(peliculaId))  // Verifica que el ID de la película coincide
            .andExpect(jsonPath("$.nombre").exists())  // Verifica que el nombre de la película existe
            .andExpect(jsonPath("$.genero").exists());  // Verifica que el género de la película existe
    }

    @Test
    void crearPelicula_debeRedirigirACorrectaVista() throws Exception {
        PeliculaDTO nuevaPelicula = new PeliculaDTO();
        nuevaPelicula.setNombre("Pelicula Test");
        nuevaPelicula.setGenero("Acción");

        mockMvc.perform(post("/peliculas")
                .param("nombre", nuevaPelicula.getNombre())
                .param("genero", nuevaPelicula.getGenero()))
            .andExpect(status().is3xxRedirection())  // Verifica que la respuesta sea una redirección
            .andExpect(redirectedUrl("/peliculas"));  // Verifica que redirige a la lista de películas
    }

    @Test
    void actualizarPelicula_debeDevolverPeliculaActualizada() throws Exception {
        Long peliculaId = 1L;  // Asegúrate de que esta película existe en la base de datos
        PeliculaDTO peliculaActualizada = new PeliculaDTO();
        peliculaActualizada.setNombre("Pelicula Actualizada");
        peliculaActualizada.setGenero("Aventura");

        mockMvc.perform(put("/peliculas/{id}", peliculaId)
                .contentType("application/json")
                .content("{\"nombre\":\"Pelicula Actualizada\", \"genero\":\"Aventura\"}"))
            .andExpect(status().isOk())  // Verifica que la respuesta es 200 OK
            .andExpect(jsonPath("$.nombre").value("Pelicula Actualizada"))  // Verifica que el nombre de la película es actualizado
            .andExpect(jsonPath("$.genero").value("Aventura"));  // Verifica que el género es actualizado
    }

    @Test
    void borrarPelicula_debeEliminarCorrectamente() throws Exception {
        Long peliculaId = 1L;  // Asegúrate de que esta película existe en la base de datos

        mockMvc.perform(delete("/peliculas/{id}", peliculaId))
            .andExpect(status().isNoContent());  // Verifica que la respuesta sea 204 No Content
    }

    @Test
    void buscarPeliculasPorGenero_yNombre_debeFiltrarPeliculasCorrectamente() throws Exception {
        mockMvc.perform(get("/peliculas")
                .param("genero", "Acción")
                .param("nombre", "Test"))
            .andExpect(status().isOk())  // Verifica que la respuesta sea 200 OK
            .andExpect(view().name("peliculas"))  // Verifica que la vista es 'peliculas'
            .andExpect(model().attributeExists("peliculas"))  // Verifica que las películas filtradas están en el modelo
            .andExpect(model().attribute("genero", "Acción"))  // Verifica que el filtro por género se pasó correctamente
            .andExpect(model().attribute("nombre", "Test"));  // Verifica que el filtro por nombre se pasó correctamente
    }
}
