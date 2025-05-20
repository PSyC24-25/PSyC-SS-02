package es.deusto.proyecto.cine.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.deusto.proyecto.cine.dto.EmisionDTO;
import es.deusto.proyecto.cine.model.Emision;
import es.deusto.proyecto.cine.model.Pelicula;
import es.deusto.proyecto.cine.model.Sala;
import es.deusto.proyecto.cine.repository.EmisionRepository;
import es.deusto.proyecto.cine.repository.PeliculaRepository;
import es.deusto.proyecto.cine.repository.SalaRepository;

public class EmisionServiceTest {
    @Mock
    private EmisionRepository emisionRepository;

    @Mock
    private SalaRepository salaRepository;

    @Mock
    private PeliculaRepository peliculaRepository;

    @InjectMocks
    private EmisionService emisionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Obtener todas las emisiones correctamente")
    void getAllEmisiones() {
        Pelicula pelicula1 = new Pelicula();
        pelicula1.setTitulo("Pelicula 1");

        Sala sala1 = new Sala();
        sala1.setNumero(1);

        Emision emision1 = new Emision();
        emision1.setCodEmision(1L);
        emision1.setFecha(LocalDateTime.now());
        emision1.setPelicula(pelicula1);
        emision1.setSala(sala1);

        Pelicula pelicula2 = new Pelicula();
        pelicula2.setTitulo("Pelicula 2");

        Sala sala2 = new Sala();
        sala2.setNumero(2);

        Emision emision2 = new Emision();
        emision2.setCodEmision(2L);
        emision2.setFecha(LocalDateTime.now());
        emision2.setPelicula(pelicula2);
        emision2.setSala(sala2);

        when(emisionRepository.findById(1L)).thenReturn(Optional.of(emision1));
        when(emisionRepository.findById(2L)).thenReturn(Optional.of(emision2));

        when(emisionRepository.findAll()).thenReturn(Arrays.asList(emision1, emision2));

        List<EmisionDTO> emisiones = emisionService.getAllEmisiones();

        assertNotNull(emisiones);
        assertEquals(2, emisiones.size());
        assertEquals(1L, emisiones.get(0).getCodEmision());
        assertEquals("Pelicula 1", emisiones.get(0).getNomPelicula());
        assertEquals(2L, emisiones.get(1).getCodEmision());
        assertEquals("Pelicula 2", emisiones.get(1).getNomPelicula());
    }

    @Test
    @DisplayName("Obtener emisión por ID correctamente")
    void getEmisionById() {
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Pelicula 1");

        Sala sala = new Sala();
        sala.setNumero(1);

        Emision emision = new Emision();
        emision.setCodEmision(1L);
        emision.setFecha(LocalDateTime.now());
        emision.setPelicula(pelicula);
        emision.setSala(sala);

        when(emisionRepository.findById(1L)).thenReturn(Optional.of(emision));

        EmisionDTO emisionDTO = emisionService.getEmisionById(1L);

        assertNotNull(emisionDTO);
        assertEquals(1L, emisionDTO.getCodEmision());
        assertEquals("Pelicula 1", emisionDTO.getNomPelicula());
        assertEquals(1, emisionDTO.getNumSala());
    }

    @Test
    @DisplayName("Crear emisión correctamente")
    void crearEmision() {
        EmisionDTO emisionDTO = new EmisionDTO();
        emisionDTO.setCodEmision(1L);
        emisionDTO.setFecha(LocalDateTime.now());
        emisionDTO.setNomPelicula("Pelicula 1");
        emisionDTO.setNumSala(1);

        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Pelicula 1");

        Sala sala = new Sala();
        sala.setNumero(1);

        when(peliculaRepository.findByTitulo("Pelicula 1")).thenReturn(Optional.of(pelicula));
        when(salaRepository.findByNumero(1)).thenReturn(Optional.of(sala));
        when(emisionRepository.save(any(Emision.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EmisionDTO result = emisionService.crearEmision(emisionDTO);

        assertNotNull(result);
        assertEquals("Pelicula 1", result.getNomPelicula());
        assertEquals(1, result.getNumSala());
    }

    @Test
    @DisplayName("Actualizar emisión correctamente")
    void actualizarEmision() {
        Emision emision = new Emision();
        emision.setCodEmision(1L);
        emision.setFecha(LocalDateTime.now());

        EmisionDTO emisionDTO = new EmisionDTO();
        emisionDTO.setFecha(LocalDateTime.now().plusDays(1));
        emisionDTO.setNomPelicula("Pelicula Actualizada");
        emisionDTO.setNumSala(2);

        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Pelicula Actualizada");

        Sala sala = new Sala();
        sala.setNumero(2);

        when(emisionRepository.findById(1L)).thenReturn(Optional.of(emision));
        when(peliculaRepository.findByTitulo("Pelicula Actualizada")).thenReturn(Optional.of(pelicula));
        when(salaRepository.findByNumero(2)).thenReturn(Optional.of(sala));
        when(emisionRepository.save(any(Emision.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EmisionDTO result = emisionService.actualizarEmision(1L, emisionDTO);

        assertNotNull(result);
        assertEquals("Pelicula Actualizada", result.getNomPelicula());
        assertEquals(2, result.getNumSala());
    }

    @Test
    @DisplayName("Borrar emisión correctamente")
    void borrarEmision() {
        when(emisionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(emisionRepository).deleteById(1L);

        assertDoesNotThrow(() -> emisionService.borrarEmision(1L));

        verify(emisionRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Borrar emisión - Emisión no encontrada")
    void borrarEmisionNoEncontrada() {
        when(emisionRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            emisionService.borrarEmision(1L);
        });

        assertEquals("No existe emision con id: 1", exception.getMessage());
    }
    
    @Test
    @DisplayName("Convertir a DTO - Pelicula no encontrada")
    void convertirADTOPeliculaNoEncontrada() {
        Sala sala = new Sala();
        sala.setNumero(1);

        Emision emision = new Emision();
        emision.setCodEmision(1L);
        emision.setFecha(LocalDateTime.now());
        emision.setSala(sala); // Pelicula is null

        when(emisionRepository.findById(1L)).thenReturn(Optional.of(emision));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            emisionService.getEmisionById(1L); // This calls convertirADTO
        });

        assertEquals("Pelicula no encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Convertir a DTO - Sala no encontrada")
    void convertirADTOSalaNoEncontrada() {
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Pelicula 1");

        Emision emision = new Emision();
        emision.setCodEmision(1L);
        emision.setFecha(LocalDateTime.now());
        emision.setPelicula(pelicula); // Sala is null

        when(emisionRepository.findById(1L)).thenReturn(Optional.of(emision));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            emisionService.getEmisionById(1L); // This calls convertirADTO
        });

        assertEquals("Sala no encontrada", exception.getMessage());
    }
}
