package es.deusto.proyecto.cine.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.deusto.proyecto.cine.dto.SalaDTO;
import es.deusto.proyecto.cine.model.Sala;
import es.deusto.proyecto.cine.repository.SalaRepository;

public class SalaServiceTest {
    @Mock
    private SalaRepository salaRepository;

    @InjectMocks
    private SalaService salaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Crear sala correctamente")
    void crearSala(){
        assertNotNull(salaRepository);
        Sala sala = new Sala();
        sala.setCodSala(1L);
        sala.setNumero(5);
        sala.setCapacidad(323);
        sala.setColumnas(18);

        when(salaRepository.save(any(Sala.class))).thenReturn(sala);

        SalaDTO salaDTO = salaService.crearSala(salaService.convertirADTO(sala));

        assertNotNull(salaDTO);
        assertEquals(sala.getNumero(), salaDTO.getNumero());
        assertEquals(salaDTO.getColumnas(), 18);
        assertTrue(sala.getEmisiones() == null);
    }

    @Test
    @DisplayName("Actualizar sala correctamente")
    void actualizarSala(){
        Sala sala = new Sala();
        sala.setCodSala(1L);
        sala.setNumero(5);
        sala.setCapacidad(323);
        sala.setColumnas(18);

        SalaDTO salaDTO = new SalaDTO(1L, 6, 150, 15);

        when(salaRepository.findById(1L)).thenReturn(Optional.of(sala));
        when(salaRepository.save(any(Sala.class))).thenReturn(sala);

        SalaDTO salaActu = salaService.convertirADTO(sala);
        assertEquals(5, salaActu.getNumero());
        assertEquals(323, salaActu.getCapacidad());
        assertEquals(18, salaActu.getColumnas());

        salaActu = salaService.actualizarSala(1L, salaDTO);

        assertEquals(6, salaActu.getNumero());
        assertEquals(150, salaActu.getCapacidad());
        assertEquals(15, salaActu.getColumnas());

    }

    @Test
    @DisplayName("Borrar sala correctamente")
    void borrarSala(){
        when(salaRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> salaService.borrarSala(1L));

        verify(salaRepository, times(1)).deleteById(1L);
    }
}
