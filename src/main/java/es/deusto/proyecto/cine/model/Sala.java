package es.deusto.proyecto.cine.model;


import jakarta.persistence.*;
import java.util.List;

/**
 * @file Sala.java
 * @brief Clase que representa una sala en el sistema de cine.
 *
 * @details
 * La clase {@code Sala} mapea la tabla "sala" en la base de datos y almacena
 * información sobre el número, capacidad y las emisiones asociadas a la sala.
 *
 * @see Emision
 *
 * @author
 * PSyC_SS_02
 * @version 1.0
 * @since 2025-05-15
 */
@Entity
public class Sala {
    /** Identificador único de la sala */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codSala;

    /** Número de la sala */
    private int numero;
    /** Capacidad de la sala */
    private int capacidad;
    /** Número de columnas de la sala */
    private int columnas;

    /** Lista de emisiones asociadas a la sala */
    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }
    
}