package es.deusto.proyecto.cine.model;


import jakarta.persistence.*;
import java.util.List;

/**
 * @file Pelicula.java
 * @brief Clase que representa una película en el sistema de cine.
 *
 * @details
 * La clase {@code Pelicula} mapea la tabla "pelicula" en la base de datos y almacena
 * información sobre el título, género, duración, director, sinopsis y las emisiones asociadas.
 *
 * @see Emision
 *
 * @author
 * PSyC_SS_02
 * @version 1.0
 * @since 2025-05-15
 */
@Entity
public class Pelicula {
    /** Identificador único de la película */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codPelicula;

    /** Título de la película */
    private String titulo;
    /** Género de la película */
    private String genero;
    /** Duración de la película en minutos */
    private int duracion; 
    /** Director de la película */
    private String director;
    /** Sinopsis de la película */
    private String sinopsis;

    /** Lista de emisiones asociadas a la película */
    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Emision> emisiones;

    
    public Pelicula() {
    }


    public Pelicula(Long codPelicula, String titulo, String genero, int duracion, String director, String sinopsis,
            List<Emision> emisiones) {
        this.codPelicula = codPelicula;
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.director = director;
        this.sinopsis = sinopsis;
        this.emisiones = emisiones;
    }


    public Long getCodPelicula() {
        return codPelicula;
    }

    public void setCodPelicula(Long codPelicula) {
        this.codPelicula = codPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public List<Emision> getEmisiones() {
        return emisiones;
    }

    public void setEmisiones(List<Emision> emisiones) {
        this.emisiones = emisiones;
    }
    
}
