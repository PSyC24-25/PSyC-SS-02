package es.deusto.proyecto.cine.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @file Compra.java
 * @brief Clase que representa una compra en el sistema de cine.
 *
 * @details
 * La clase {@code Compra} mapea la tabla "compra" en la base de datos y almacena
 * información sobre el usuario, los asientos seleccionados, el precio total y la emisión asociada.
 *
 * @see Usuario
 * @see Emision
 *
 * @author
 * PSyC_SS_02
 * @version 1.0
 * @since 2025-05-15
 */
@Entity
public class Compra {

    /** Identificador único de la compra */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codCompra;

    /** Usuario que realizó la compra */
    @ManyToOne
    @JoinColumn(name = "codUsuario", nullable = false)
    private Usuario usuario;

    /** Emisión asociada a la compra */
    @ManyToOne
    @JoinColumn(name = "codEmision", nullable = false)
    private Emision emision;

    /** Lista de asientos seleccionados para la compra */
    @ElementCollection
    @CollectionTable(
        name = "asiento",
        joinColumns = @JoinColumn(name = "codCompra") 
    )
    @Column(name = "asiento")
    private List<String> asientos = new ArrayList<>();

    /** Precio total de la compra */
    private double precio;

    
    public Compra() {
    }

    public Compra(Long codCompra, Usuario usuario, Emision emision, List<String> asientos, double precio) {
        this.codCompra = codCompra;
        this.usuario = usuario;
        this.emision = emision;
        this.asientos = asientos;
        this.precio = precio;
    }


    public Long getCodCompra() {
        return codCompra;
    }

    public void setCodCompra(Long codCompra) {
        this.codCompra = codCompra;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Emision getEmision() {
        return emision;
    }

    public void setEmision(Emision emision) {
        this.emision = emision;
    }

    public List<String> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<String> asientos) {
        this.asientos = asientos;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    
}
