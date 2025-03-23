package es.deusto.proyecto.cine.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codCompra;

    @Column(name = "codUsuario", nullable = false)
    private Long usuario;

    @Column(name = "codEmision", nullable = false)
    private Long emision;

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

    public Compra(Long codCompra, Long usuario, Long emision, List<String> asientos, double precio) {
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

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public Long getEmision() {
        return emision;
    }

    public void setEmision(Long emision) {
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
