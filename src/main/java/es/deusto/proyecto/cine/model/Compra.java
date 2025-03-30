package es.deusto.proyecto.cine.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codCompra;

    @ManyToOne
    @JoinColumn(name = "codUsuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "codEmision", nullable = false)
    @JsonBackReference
    private Emision emision;

    @ElementCollection
    @CollectionTable(
        name = "asiento",
        joinColumns = @JoinColumn(name = "codCompra") 
    )
    @Column(name = "asiento")
    private List<String> asientos = new ArrayList<>();

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
