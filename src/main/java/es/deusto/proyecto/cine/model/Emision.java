package es.deusto.proyecto.cine.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @file Emision.java
 * @brief Clase que representa una emisión de película en el sistema de cine.
 *
 * @details
 * La clase {@code Emision} mapea la tabla "emision" en la base de datos y almacena
 * información sobre la fecha y hora de la emisión, la película asociada, la sala donde se proyecta
 * y las compras realizadas para esa emisión.
 *
 * @see Pelicula
 * @see Sala
 * @see Compra
 *
 * @author
 * PSyC_SS_02
 * @version 1.0
 * @since 2025-05-15
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
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
