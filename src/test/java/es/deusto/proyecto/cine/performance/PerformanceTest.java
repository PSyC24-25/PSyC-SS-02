package es.deusto.proyecto.cine.performance;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.noconnor.junitperf.JUnitPerfInterceptor;
import com.github.noconnor.junitperf.JUnitPerfReportingConfig;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.JUnitPerfTestActiveConfig;
import com.github.noconnor.junitperf.JUnitPerfTestRequirement;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

import es.deusto.proyecto.cine.dto.CompraDTO;
import es.deusto.proyecto.cine.dto.EmisionDTO;
import es.deusto.proyecto.cine.dto.PeliculaDTO;
import es.deusto.proyecto.cine.dto.SalaDTO;
import es.deusto.proyecto.cine.model.Pelicula;
import es.deusto.proyecto.cine.model.Rol;
import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.service.CompraService;
import es.deusto.proyecto.cine.service.EmisionService;
import es.deusto.proyecto.cine.service.PeliculaService;
import es.deusto.proyecto.cine.service.SalaService;
import es.deusto.proyecto.cine.service.UsuarioService;

@SpringBootTest
@ExtendWith(JUnitPerfInterceptor.class)
public class PerformanceTest {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private EmisionService emisionService;

    @Autowired
    private SalaService salaService;

    @Autowired
    private CompraService compraService;

    @JUnitPerfTestActiveConfig
    private final static JUnitPerfReportingConfig PERF_CONFIG = JUnitPerfReportingConfig.builder()
            .reportGenerator(new HtmlReportGenerator(System.getProperty("user.dir") + "/target/reports/perf-report.html"))
            .build();
    
    //Performance para usuarios
    @Test
    @JUnitPerfTest(threads = 25, durationMs = 2500, warmUpMs = 2000)
    @JUnitPerfTestRequirement(executionsPerSec = 35, percentiles = "95:200ms")
    public void testGetAllUsuariosPerformance() {
        usuarioService.getAllUsuarios();
    }

    @Test
    @JUnitPerfTest(threads = 15, durationMs = 2000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(executionsPerSec = 25, percentiles = "95:300ms")
    public void testCrearUsuarioPerformance() {
        Usuario usuario = new Usuario(1L, "Juan", "Rodriguez", "Juan@gmail.com", "123456789", "password", null, Rol.USUARIO);
        usuarioService.crearUsuario(usuario);
    }

    //Performance para peliculas
    @Test
    @JUnitPerfTest(threads = 20, durationMs = 2000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(executionsPerSec = 35, percentiles = "95:300ms")
    public void testGetAllPeliculasPerformance() {
        peliculaService.getAllPeliculas();
    }

    @Test
    @JUnitPerfTest(threads = 15, durationMs = 2000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(executionsPerSec = 25, percentiles = "95:300ms")
    public void testCrearPeliculasPerformance() {
        Pelicula pelicula = new Pelicula(1L, "Avatar", "Accion", 120, "Director", "Sinopsis jdahdkahd akjdahskd adadad", null);
        PeliculaDTO peliculaDTO = peliculaService.convertirADTO(pelicula);
        peliculaService.crearPelicula(peliculaDTO);
    }

    //Performance para compras
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(executionsPerSec = 20, percentiles = "90:300ms")
    public void testGetAllComprasPerformance() {
        compraService.getAllCompras();
    }

    @Test
    @JUnitPerfTest(threads = 15, durationMs = 2000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(executionsPerSec = 25, percentiles = "95:300ms")
    public void testCrearComprasPerformance() {
        CompraDTO compraDTO = new CompraDTO(1L, 1L, 1L, null);
        compraService.crearCompra(compraDTO);
    }

    //Performance para salas
    @Test
    @JUnitPerfTest(threads = 20, durationMs = 2000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(executionsPerSec = 35, percentiles = "95:300ms")
    public void testGetAllSalasPerformance() {
        salaService.getAllSalas();
    }

    @Test
    @JUnitPerfTest(threads = 15, durationMs = 2000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(executionsPerSec = 25, percentiles = "95:300ms")
    public void testCrearSalasPerformance() {
        SalaDTO salaDTO = new SalaDTO(1L, 1, 100, 14);
        salaService.crearSala(salaDTO);
    }

    //Performance para emisiones
    @Test
    @JUnitPerfTest(threads = 20, durationMs = 2000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(executionsPerSec = 35, percentiles = "95:300ms")
    public void testGetAllEmisionPerformance() {
        emisionService.getAllEmisiones();
    }

    @Test
    @JUnitPerfTest(threads = 15, durationMs = 2000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(executionsPerSec = 25, percentiles = "95:300ms")
    public void testCrearEmisionesPerformance() {
        EmisionDTO emisionDTO = new EmisionDTO(1L, LocalDateTime.now(), "peli", 1);
        emisionService.crearEmision(emisionDTO);
    }
}
