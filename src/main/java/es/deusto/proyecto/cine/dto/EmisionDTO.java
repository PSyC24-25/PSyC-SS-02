package es.deusto.proyecto.cine.dto;

import java.time.LocalDateTime;

public class EmisionDTO {
    private Long codEmision;
    private LocalDateTime fecha; 
    private String nomPelicula;
    private Integer numSala;

    public EmisionDTO() {
    }

    public EmisionDTO(Long codEmision, LocalDateTime fecha, String nomPelicula, Integer numSala) {
        this.codEmision = codEmision;
        this.fecha = fecha;
        this.nomPelicula = nomPelicula;
        this.numSala = numSala;
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

    public String getNomPelicula() {
        return nomPelicula;
    }

    public void setNomPelicula(String nomPelicula) {
        this.nomPelicula = nomPelicula;
    }

    public Integer getNumSala() {
        return numSala;
    }

    public void setNumSala(Integer numSala) {
        this.numSala = numSala;
    }
}
