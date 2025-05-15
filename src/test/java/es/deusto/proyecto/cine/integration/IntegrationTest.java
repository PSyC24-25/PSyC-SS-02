package es.deusto.proyecto.cine.integration;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import es.deusto.proyecto.cine.CineApplication;
import es.deusto.proyecto.cine.model.Usuario;

@SpringBootTest(classes = CineApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntegrationTest {

    private final int port = 8080;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getUrl(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    public void testCrearYEliminarUsuario() {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCorreo("test@correo.com");
        nuevoUsuario.setContrasenya("password123");
        nuevoUsuario.setNombre("Test");
        nuevoUsuario.setApellido("Usuario");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Usuario> request = new HttpEntity<>(nuevoUsuario, headers);

        //Creación de usuario
        ResponseEntity<Usuario> response = restTemplate.postForEntity(
                getUrl("/usuarios"), request, Usuario.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCorreo()).isEqualTo("test@correo.com");

        //Eliminación de usuario
        Long idUsuario = response.getBody().getCodUsuario();

        assertThat(idUsuario).isNotNull();

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
            getUrl("/usuarios/" + idUsuario), 
            org.springframework.http.HttpMethod.DELETE,
            null,
            Void.class
        );

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
