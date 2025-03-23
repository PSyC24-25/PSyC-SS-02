package es.deusto.proyecto.cine.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Emision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codEmision;

    private LocalDateTime fecha; 

    @Column(name = "codPelicula", nullable = false)
    private Long pelicula;

    @Column(name = "codSala", nullable = false)
    private Long sala;

    @OneToMany(mappedBy = "emision", cascade = CascadeType.ALL)
    private List<Compra> compras;


    public Emision() {
    }

    public Emision(Long codEmision, LocalDateTime fecha, Long pelicula, Long sala, List<Compra> compras) {
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

    public Long getPelicula() {
        return pelicula;
    }

    public void setPelicula(Long pelicula) {
        this.pelicula = pelicula;
    }

    public Long getSala() {
        return sala;
    }

    public void setSala(Long sala) {
        this.sala = sala;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    
}
