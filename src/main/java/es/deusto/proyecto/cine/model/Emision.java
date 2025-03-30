package es.deusto.proyecto.cine.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Emision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codEmision;

    private LocalDateTime fecha; 

    @ManyToOne
    @JoinColumn(name = "codPelicula", nullable = false)
    @JsonBackReference
    private Pelicula pelicula;

    @ManyToOne
    @JoinColumn(name = "codSala", nullable = false)
    @JsonBackReference
    private Sala sala;

    @OneToMany(mappedBy = "emision", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Compra> compras;


    public Emision() {
    }

    public Emision(Long codEmision, LocalDateTime fecha, Pelicula pelicula, Sala sala, List<Compra> compras) {
        this.codEmision = codEmision;
        this.fecha = fecha;
        this.pelicula = pelicula;
        this.sala = sala;
        this.compras = compras;
    }

    public Long getCodEmision() {
        return codEmision;
    }

    public void setCodEmision(Long codEmision) {
        this.codEmision = codEmision;
    }

    public LocalDateTime getDateTime() {
        return fecha;
    }

    public void setDateTime(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    
}
