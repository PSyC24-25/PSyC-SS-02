package es.deusto.proyecto.cine.dto;

import java.util.List;

public class CompraDTO {
    private Long codCompra;
    private Long idUsuario;
    private Long idEmision;
    private List<String> asientos;
    
    public CompraDTO() {
    }
    
    public CompraDTO(Long codCompra, Long idUsuario, Long idEmision, List<String> asientos) {
        this.codCompra = codCompra;
        this.idUsuario = idUsuario;
        this.idEmision = idEmision;
        this.asientos = asientos;
    }

    public Long getCodCompra() {
        return codCompra;
    }

    public void setCodCompra(Long codCompra) {
        this.codCompra = codCompra;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdEmision() {
        return idEmision;
    }

    public void setIdEmision(Long idEmision) {
        this.idEmision = idEmision;
    }

    public List<String> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<String> asientos) {
        this.asientos = asientos;
    }

    
}
