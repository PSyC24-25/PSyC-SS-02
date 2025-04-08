package es.deusto.proyecto.cine.dto;

import java.time.LocalDateTime;

public class EmisionDTO {
    private Long codEmision;
    private LocalDateTime fecha; 
    private String nomPelicula;
    private int numSala;

    public EmisionDTO() {
    }

    public EmisionDTO(Long codEmision, LocalDateTime fecha, String nomPelicula, int numSala) {
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

    public void setIdPelicula(String nomPelicula) {
        this.nomPelicula = nomPelicula;
    }

    public int getNumSala() {
        return numSala;
    }

    public void setNumSala(int numSala) {
        this.numSala = numSala;
    }
}
