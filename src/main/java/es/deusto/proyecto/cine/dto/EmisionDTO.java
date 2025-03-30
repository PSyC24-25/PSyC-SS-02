package es.deusto.proyecto.cine.dto;

import java.time.LocalDateTime;

public class EmisionDTO {
    private Long codEmision;
    private LocalDateTime fecha; 
    private Long idPelicula;
    private Long idSala;

    public EmisionDTO() {
    }

    public EmisionDTO(Long codEmision, LocalDateTime fecha, Long idPelicula, Long idSala) {
        this.codEmision = codEmision;
        this.fecha = fecha;
        this.idPelicula = idPelicula;
        this.idSala = idSala;
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

    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public Long getIdSala() {
        return idSala;
    }

    public void setIdSala(Long idSala) {
        this.idSala = idSala;
    }
}
