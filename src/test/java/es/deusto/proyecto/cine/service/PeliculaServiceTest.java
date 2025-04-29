package es.deusto.proyecto.cine.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.deusto.proyecto.cine.dto.PeliculaDTO;
import es.deusto.proyecto.cine.model.Pelicula;
import es.deusto.proyecto.cine.repository.PeliculaRepository;

public class PeliculaServiceTest {
    @Mock
    private PeliculaRepository peliculaRepository;

    @InjectMocks
    private PeliculaService peliculaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    @DisplayName("Obtener todas las películas correctamente")
    void getAllPeliculas() {
        Pelicula pelicula1 = new Pelicula();
        pelicula1.setCodPelicula(1L);
        pelicula1.setTitulo("Pelicula 1");

        Pelicula pelicula2 = new Pelicula();
        pelicula2.setCodPelicula(2L);
        pelicula2.setTitulo("Pelicula 2");

        when(peliculaRepository.findAll()).thenReturn(Arrays.asList(pelicula1, pelicula2));

        List<PeliculaDTO> peliculas = peliculaService.getAllPeliculas();

        assertNotNull(peliculas);
        assertEquals(2, peliculas.size());
        assertEquals("Pelicula 1", peliculas.get(0).getTitulo());
        assertEquals("Pelicula 2", peliculas.get(1).getTitulo());
    }

    @Test
    @DisplayName("Buscar películas por nombre y género")
    void buscarPeliculas() {
        Pelicula pelicula1 = new Pelicula();
        pelicula1.setTitulo("Acción épica");
        pelicula1.setGenero("Acción");

        Pelicula pelicula2 = new Pelicula();
        pelicula2.setTitulo("Comedia divertida");
        pelicula2.setGenero("Comedia");

        when(peliculaRepository.findAll()).thenReturn(Arrays.asList(pelicula1, pelicula2));

        List<PeliculaDTO> peliculas = peliculaService.buscarPeliculas("épica", "Acción");

        assertNotNull(peliculas);
        assertEquals(1, peliculas.size());
        assertEquals("Acción épica", peliculas.get(0).getTitulo());
    }

    @Test
    @DisplayName("Obtener películas por género")
    void getPeliculasByGenero() {
        Pelicula pelicula = new Pelicula();
        pelicula.setGenero("Acción");

        when(peliculaRepository.findByGenero("Acción")).thenReturn(Arrays.asList(pelicula));

        List<PeliculaDTO> peliculas = peliculaService.getPeliculasByGenero("Acción");

        assertNotNull(peliculas);
        assertEquals(1, peliculas.size());
        assertEquals("Acción", peliculas.get(0).getGenero());
    }

    @Test
    @DisplayName("Obtener película por ID correctamente")
    void getPeliculaById() {
        Pelicula pelicula = new Pelicula();
        pelicula.setCodPelicula(1L);
        pelicula.setTitulo("Pelicula 1");

        when(peliculaRepository.findById(1L)).thenReturn(Optional.of(pelicula));

        PeliculaDTO peliculaDTO = peliculaService.getPeliculaById(1L);

        assertNotNull(peliculaDTO);
        assertEquals("Pelicula 1", peliculaDTO.getTitulo());
    }

    @Test
    @DisplayName("Obtener película por título correctamente")
    void getPeliculaByTitulo() {
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Pelicula 1");

        when(peliculaRepository.findByTitulo("Pelicula 1")).thenReturn(Optional.of(pelicula));

        Pelicula result = peliculaService.getPeliculaByTitulo("Pelicula 1");

        assertNotNull(result);
        assertEquals("Pelicula 1", result.getTitulo());
    }

    @Test
    @DisplayName("Crear película correctamente")
    void crearPelicula() {
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Nueva Pelicula");

        PeliculaDTO peliculaDTO = new PeliculaDTO();
        peliculaDTO.setTitulo("Nueva Pelicula");

        when(peliculaRepository.save(any(Pelicula.class))).thenReturn(pelicula);

        PeliculaDTO result = peliculaService.crearPelicula(peliculaDTO);

        assertNotNull(result);
        assertEquals("Nueva Pelicula", result.getTitulo());
    }

    @Test
    @DisplayName("Actualizar película correctamente")
    void actualizarPelicula() {
        Pelicula pelicula = new Pelicula();
        pelicula.setCodPelicula(1L);
        pelicula.setTitulo("Pelicula Original");

        PeliculaDTO peliculaDTO = new PeliculaDTO();
        peliculaDTO.setCodPelicula(1L);
        peliculaDTO.setTitulo("Pelicula Actualizada");

        when(peliculaRepository.findById(1L)).thenReturn(Optional.of(pelicula));
        when(peliculaRepository.save(any(Pelicula.class))).thenReturn(pelicula);

        PeliculaDTO result = peliculaService.actualizarPelicula(1L, peliculaDTO);

        assertNotNull(result);
        assertEquals("Pelicula Actualizada", result.getTitulo());
    }

    @Test
    @DisplayName("Borrar película correctamente")
    void borrarPelicula() {
        Pelicula pelicula = new Pelicula();
        pelicula.setCodPelicula(1L);

        when(peliculaRepository.findByCodPelicula(1L)).thenReturn(Optional.of(pelicula));
        doNothing().when(peliculaRepository).deleteByCodPelicula(1L);

        assertDoesNotThrow(() -> peliculaService.borrarPelicula(1L));

        verify(peliculaRepository, times(1)).deleteByCodPelicula(1L);
    }
}