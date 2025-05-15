package es.deusto.proyecto.cine.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase que representa una emisión de una película en una sala de cine.
 * 
 * Esta clase contiene información sobre la fecha y hora de la emisión,
 * la película asociada, la sala donde se proyecta y las compras realizadas
 * para esa emisión.
 */
@Entity
public class Emision {
    /** Identificador único de la emisión */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codEmision;

    /** Fecha y hora de la emisión */
    private LocalDateTime fecha; 

    /** Película asociada a la emisión */
    @ManyToOne
    @JoinColumn(name = "codPelicula", nullable = false)
    private Pelicula pelicula;

    /** Sala donde se proyecta la película */
    @ManyToOne
    @JoinColumn(name = "codSala", nullable = false)
    private Sala sala;

    /** Lista de compras realizadas para esta emisión */
    @OneToMany(mappedBy = "emision", orphanRemoval = true, fetch = FetchType.LAZY)
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
