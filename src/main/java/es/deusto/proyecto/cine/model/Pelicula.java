package main.java.es.deusto.proyecto.cine.model;


import jakarta.persistence.*;
import java.util.List;

@Entity
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codPelicula;

    private String titulo;
    private String genero;
    private int duracion; 
    private String director;
    private String sinopsis;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL)
    private List<Emision> emisiones;

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

    public List<Usuario> getCompras() {
        return compras;
    }

    public void setCompras(List<Usuario> compras) {
        this.compras = compras;
    }

    
}
