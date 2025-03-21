package main.java.es.deusto.proyecto.cine.model;


import jakarta.persistence.*;
import java.util.List;

@Entity
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codSala;

    private int numero;
    private int capacidad;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
    private List<Emision> emisiones;

    public Sala() {
    }

    public Sala(Long codSala, int numero, int capacidad, List<Emision> emisiones) {
        this.codSala = codSala;
        this.numero = numero;
        this.capacidad = capacidad;
        this.emisiones = emisiones;
    }


    public Long getCodSala() {
        return codSala;
    }

    public void setCodSala(Long codSala) {
        this.codSala = codSala;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Emision> getEmisiones() {
        return emisiones;
    }

    public void setEmisiones(List<Emision> emisiones) {
        this.emisiones = emisiones;
    }

    
}