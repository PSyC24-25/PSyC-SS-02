package es.deusto.proyecto.cine.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Emision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codEmision;

    private LocalDateTime dateTime; 

    @ManyToOne
    @JoinColumn(name = "codPelicula", nullable = false)
    private Pelicula pelicula;

    @ManyToOne
    @JoinColumn(name = "codSala", nullable = false)
    private Sala sala;

    @OneToMany(mappedBy = "emison", cascade = CascadeType.ALL)
    private List<Usuario> compras;


    public Emision() {
    }

    public Emision(Long codEmision, LocalDateTime dateTime, Pelicula pelicula, Sala sala, List<Usuario> compras) {
        this.codEmision = codEmision;
        this.dateTime = dateTime;
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
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    public List<Usuario> getCompras() {
        return compras;
    }

    public void setCompras(List<Usuario> compras) {
        this.compras = compras;
    }

    
}
