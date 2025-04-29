package es.deusto.proyecto.cine.integration;

import es.deusto.proyecto.cine.dto.UsuarioDTO;
import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        // Preparar un usuario para las pruebas
        usuario = new Usuario();
        usuario.setNombre("Juan Perez");
        usuario.setCorreo("juan.perez@example.com");
        usuario.setContrasenya("password123");
        usuario.setRol(null); // Asigna un rol si lo tienes configurado
        usuarioRepository.save(usuario);
    }

    @Test
    public void testGetAllUsuarios() {
        // Ejecuta la llamada al endpoint /usuarios
        ResponseEntity<UsuarioDTO[]> response = restTemplate.getForEntity("/usuarios", UsuarioDTO[].class);

        // Verifica que la respuesta sea 200 OK
        assertEquals(200, response.getStatusCodeValue());

        // Verifica que el número de usuarios sea correcto
        assertTrue(response.getBody().length > 0);
    }

    @Test
    public void testGetUsuarioById() {
        // Llama al endpoint para obtener un usuario por ID
        ResponseEntity<UsuarioDTO> response = restTemplate.getForEntity("/usuarios/{id}", UsuarioDTO.class, usuario.getCodUsuario());

        // Verifica que la respuesta sea 200 OK
        assertEquals(200, response.getStatusCodeValue());

        // Verifica que el usuario devuelto tiene el mismo ID que el creado
        assertEquals(usuario.getCodUsuario(), response.getBody().getCodUsuario());
    }

    @Test
    public void testCreateUsuario() {
        // Crea un nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("Carlos Gomez");
        nuevoUsuario.setCorreo("carlos.gomez@example.com");
        nuevoUsuario.setContrasenya("newpassword");
        nuevoUsuario.setRol(null); // Asigna un rol si lo tienes configurado

        // Llama al endpoint para crear el nuevo usuario
        ResponseEntity<UsuarioDTO> response = restTemplate.postForEntity("/usuarios", nuevoUsuario, UsuarioDTO.class);

        // Verifica que la respuesta sea 201 Created
        assertEquals(201, response.getStatusCodeValue());

        // Verifica que el nombre y el correo del usuario coinciden
        assertEquals(nuevoUsuario.getNombre(), response.getBody().getNombre());
        assertEquals(nuevoUsuario.getCorreo(), response.getBody().getCorreo());
    }

    @Test
    public void testUpdateUsuario() {
        // Modifica el usuario existente
        usuario.setNombre("Juan Perez Actualizado");
        usuario.setCorreo("juan.perez.updated@example.com");

        // Llama al endpoint para actualizar el usuario
        restTemplate.put("/usuarios/{id}", usuario, usuario.getCodUsuario());

        // Verifica que el usuario ha sido actualizado correctamente
        ResponseEntity<UsuarioDTO> response = restTemplate.getForEntity("/usuarios/{id}", UsuarioDTO.class, usuario.getCodUsuario());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Juan Perez Actualizado", response.getBody().getNombre());
        assertEquals("juan.perez.updated@example.com", response.getBody().getCorreo());
    }

    @Test
    public void testDeleteUsuario() {
        // Llama al endpoint para borrar el usuario
        restTemplate.delete("/usuarios/{id}", usuario.getCodUsuario());

        // Verifica que el usuario ha sido eliminado
        ResponseEntity<UsuarioDTO> response = restTemplate.getForEntity("/usuarios/{id}", UsuarioDTO.class, usuario.getCodUsuario());
        assertEquals(404, response.getStatusCodeValue()); // Verifica que la respuesta es 404 Not Found
    }
}

