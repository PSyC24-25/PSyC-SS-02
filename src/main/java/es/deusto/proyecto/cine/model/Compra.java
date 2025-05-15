package es.deusto.proyecto.cine.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una compra de entradas en el sistema de cine.
 * 
 * Esta clase contiene información sobre la compra, incluyendo el usuario
 * que realizó la compra, la emisión asociada, los asientos seleccionados
 * y el precio total de la compra.
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
