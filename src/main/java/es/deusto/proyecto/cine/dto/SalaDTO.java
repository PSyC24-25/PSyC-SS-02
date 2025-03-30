package es.deusto.proyecto.cine.dto;


public class SalaDTO {
    private Long codSala;
    private int numero;
    private int capacidad;

    public SalaDTO() {
    }

    public SalaDTO(Long codSala, int numero, int capacidad) {
        this.codSala = codSala;
        this.numero = numero;
        this.capacidad = capacidad;
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
}
