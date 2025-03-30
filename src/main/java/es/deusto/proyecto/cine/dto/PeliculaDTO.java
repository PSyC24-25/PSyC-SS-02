package es.deusto.proyecto.cine.dto;


public class PeliculaDTO {
    private Long codPelicula;
    private String titulo;
    private String genero;
    private int duracion; 
    private String director;
    private String sinopsis;


    public PeliculaDTO() {
    }

    public PeliculaDTO(Long codPelicula, String titulo, String genero, int duracion, String director, String sinopsis) {
        this.codPelicula = codPelicula;
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.director = director;
        this.sinopsis = sinopsis;
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

}
