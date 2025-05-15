package es.deusto.proyecto.cine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.deusto.proyecto.cine.dto.EmisionDTO;
import es.deusto.proyecto.cine.dto.PeliculaDTO;
import es.deusto.proyecto.cine.dto.SalaDTO;
import es.deusto.proyecto.cine.dto.UsuarioDTO;
import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.repository.UsuarioRepository;
import es.deusto.proyecto.cine.service.EmisionService;
// import es.deusto.proyecto.cine.model.Usuario;
import es.deusto.proyecto.cine.service.PeliculaService;
import es.deusto.proyecto.cine.service.SalaService;
import es.deusto.proyecto.cine.service.UsuarioService;

/**
 * Clase que representa el controlador de administración en el sistema de cine.
 * 
 * Esta clase contiene métodos para gestionar películas, emisiones y usuarios.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PeliculaService peliculaService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private final EmisionService emisionService;

    private final SalaService salaService;

    private final UsuarioService usuarioService;

    public AdminController(PeliculaService peliculaService, EmisionService emisionService, SalaService salaService, UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.salaService = salaService;
        this.emisionService = emisionService;
        this.peliculaService = peliculaService;
    }

    // PANEL ADMIN
    /**
     * Muestra el panel de administración.
     *
     * @param authentication la autenticación del usuario
     * @param model el modelo para pasar datos a la vista
     * @return la vista del panel de administración
     */
    @GetMapping
    public String panelAdmin(Authentication authentication, Model model) {
        if (authentication != null) {
            String nombre = authentication.getName();
            Usuario usuario = usuarioRepository.findByCorreo(nombre).orElse(null);
            model.addAttribute("usuario", usuario);
        }
        return "panel_admin";  
    }


    //PELICULAS
    /**
     * Muestra la lista de películas en el panel de administración.
     *
     * @param model el modelo para pasar datos a la vista
     */
    @GetMapping("/peliculas")
    public String getPeliculasAdmin(Model model) {
        List<PeliculaDTO> peliculas = peliculaService.getAllPeliculas();
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("nuevaPelicula", new PeliculaDTO());
        return "admin_peliculas";
    }

    /**
     * Agrega una nueva película al sistema.
     *
     * @param peliculaDTO el objeto DTO de la película a agregar
     * @return redirige a la lista de películas
     */
    @PostMapping("/peliculas/agregar")
    public String agregarPelicula(@ModelAttribute PeliculaDTO peliculaDTO) {
        peliculaService.crearPelicula(peliculaDTO);
        return "redirect:/admin/peliculas";
    }

    /**
     * Elimina una película del sistema.
     *
     * @param id el identificador de la película a eliminar
     * @return redirige a la lista de películas
     */
    @DeleteMapping("/peliculas/borrar/{id}")
    public String borrarPelicula(@PathVariable Long id) {
        peliculaService.borrarPelicula(id);
        return "redirect:/admin/peliculas";
    }

    /**
     * Actualiza una película existente en el sistema.
     *
     * @param peliculaDTO el objeto DTO de la película a actualizar
     * @return redirige a la lista de películas
     */
    @PutMapping("/peliculas/editar")
    public String actualizarPelicula(@ModelAttribute PeliculaDTO peliculaDTO) {
        peliculaService.actualizarPelicula(peliculaDTO.getCodPelicula(), peliculaDTO);
        return "redirect:/admin/peliculas";
    }

    /**
     * Muestra el formulario de edición de una película.
     *
     * @param id el identificador de la película a editar
     * @param model el modelo para pasar datos a la vista
     * @return la vista del formulario de edición de película
     */
    @GetMapping("/peliculas/editar/{id}")
    public String editarPelicula(@PathVariable Long id, Model model) {
        PeliculaDTO pelicula = peliculaService.getPeliculaById(id);
        model.addAttribute("pelicula", pelicula);
        return "admin_pelicula_editar";
    }


    //EMISIONES
    /**
     * Muestra la lista de emisiones en el panel de administración.
     *
     * @param model el modelo para pasar datos a la vista
     * @return la vista de gestión de emisiones
     */
    @GetMapping("/emisiones")
    public String gestionarEmisiones(Model model) {
        model.addAttribute("nuevaEmision", new EmisionDTO());

        // Aquí asumimos que tienes un método que devuelve entidades Pelicula
        List<PeliculaDTO> peliculas = peliculaService.getAllPeliculas(); 
        List<SalaDTO> salas = salaService.getAllSalas();

        model.addAttribute("peliculas", peliculas);
        model.addAttribute("salas", salas);

        return "admin_emisiones";
    }

    /**
     * Agrega una nueva emisión al sistema.
     *
     * @param emisionDTO el objeto DTO de la emisión a agregar
     * @return redirige a la lista de emisiones
     */
    @PostMapping("/emisiones/programar")
    public String programarEmision(@ModelAttribute EmisionDTO emisionDTO) {
        emisionService.crearEmision(emisionDTO);
        return "redirect:/admin/emisiones";
        // Emision emision = new Emision();
        // emision.setDateTime(emisionDTO.getFecha());

        // Pelicula pelicula = peliculaService.getPeliculaByTitulo(emisionDTO.getNomPelicula());
        // emision.setPelicula(pelicula);

        // Sala sala = salaService.getSalaByNumero(emisionDTO.getNumSala());
        // emision.setSala(sala);

        // emisionService.guardar(emision);
        // return "redirect:/admin/emisiones";
    }


    //USUARIOS
    /**
     * Muestra la lista de usuarios en el panel de administración.
     *
     * @param model el modelo para pasar datos a la vista
     * @return la vista de gestión de usuarios
     */
    @GetMapping("/usuarios")
    public String getUsuariosAdmin(Model model) {
        List<UsuarioDTO> usuarios = usuarioService.getAllUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "admin_usuarios";
    }

    /**
     * Agrega un nuevo usuario al sistema.
     *
     * @param usuarioDTO el objeto DTO del usuario a agregar
     * @return redirige a la lista de usuarios
     */
    @PutMapping("/usuarios/editar")
    public String actualizarUsuario(@ModelAttribute UsuarioDTO usuarioDTO) {
        usuarioService.actualizarUsuario(usuarioDTO.getCodUsuario(), usuarioDTO);
        return "redirect:/admin/usuarios";
    }

    /**
     * Agrega un nuevo usuario al sistema.
     *
     * @param usuarioDTO el objeto DTO del usuario a agregar
     * @return redirige a la lista de usuarios
     */
    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        UsuarioDTO usuario = usuarioService.getUsuarioById(id);
        model.addAttribute("usuario", usuario);
        return "admin_usuario_editar";
    }

    /**
     * Elimina un usuario del sistema.
     *
     * @param id el identificador del usuario a eliminar
     * @return redirige a la lista de usuarios
     */
    @DeleteMapping("/usuarios/borrar/{id}")
    public String borrarUsuario(@PathVariable Long id) {
        usuarioService.borrarUsuario(id);
        return "redirect:/admin/usuarios";
    }


    //SALAS
    /**
     * Muestra la lista de salas en el panel de administración.
     *
     * @param model el modelo para pasar datos a la vista
     * @return la vista de gestión de salas
     */
    @GetMapping("/salas")
    public String getSalasAdmin(Model model) {
        List<SalaDTO> salas = salaService.getAllSalas();
        model.addAttribute("salas", salas);
        model.addAttribute("nuevaSala", new SalaDTO());
        return "admin_salas";
    }

    /**
     * Agrega una nueva sala al sistema.
     *
     * @param salaDTO el objeto DTO de la sala a agregar
     * @return redirige a la lista de salas
     */
    @PostMapping("/salas/agregar")
    public String agregarSala(@ModelAttribute SalaDTO salaDTO) {
        salaService.crearSala(salaDTO);
        return "redirect:/admin/salas";
    }

    /**
     * Actualiza una sala existente en el sistema.
     *
     * @param salaDTO el objeto DTO de la sala a actualizar
     * @return redirige a la lista de salas
     */
    @DeleteMapping("/salas/borrar/{id}")
    public String borrarSala(@PathVariable Long id) {
        salaService.borrarSala(id);
        return "redirect:/admin/salas";
    }

}