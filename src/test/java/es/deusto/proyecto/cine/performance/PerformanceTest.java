package es.deusto.proyecto.cine.performance;

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
    
    @Test
    @JUnitPerfTest(threads = 25, durationMs = 10000, warmUpMs = 3000)
    @JUnitPerfTestRequirement(executionsPerSec = 35, percentiles = "95:200ms")
    public void testGetAllUsuariosPerformance() {
        usuarioService.getAllUsuarios();
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 5000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(executionsPerSec = 35, percentiles = "95:300ms")
    public void testGetAllPeliculasPerformance() {
        peliculaService.getAllPeliculas();
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 5000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(executionsPerSec = 20, percentiles = "90:300ms")
    public void testGetAllComprasPerformance() {
        peliculaService.getAllPeliculas();
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 5000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(executionsPerSec = 35, percentiles = "95:300ms")
    public void testGetAllSalasPerformance() {
        salaService.getAllSalas();
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 5000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(executionsPerSec = 35, percentiles = "95:300ms")
    public void testGetAllEmisionPerformance() {
        emisionService.getAllEmisiones();
    }
}
