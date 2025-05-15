package es.deusto.proyecto.cine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Controlador para gestionar la tienda.
 */
@Controller
public class TiendaController {

    private static final String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ID_LENGTH = 4;
    private final Random random = new SecureRandom();

    /**
     * Genera un ID de compra aleatorio.
     *
     * @return un ID de compra aleatorio
     */
    private String generarIdCompra() {
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            sb.append(ALPHA_NUMERIC.charAt(random.nextInt(ALPHA_NUMERIC.length())));
        }
        return sb.toString();
    }

    /**
     * Muestra la vista de la tienda.
     *
     * @param model el modelo para pasar datos a la vista
     * @return la vista de la tienda
     */
    @GetMapping("/tienda")
    public String mostrarTienda(Model model) {
        model.addAttribute("idCompra", generarIdCompra());
        return "tienda";
    }

    /**
     * Procesa la compra.
     *
     * @return la redirección a la página de inicio
     */
    @PostMapping("/tienda/comprar")
    public String procesarCompra() {
        // Aquí puedes guardar los datos si es necesario
        return "redirect:/"; // Redirige de nuevo al inicio
    }
}
