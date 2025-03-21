package main.java.es.deusto.proyecto.cine.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codUsuario;

    private String nombre;
    private String apellido;
    private String correo;
    private String numTelefono;
    private String contrasenya;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Compra> compras;

    public Usuario() {
    }

    public Usuario(Long codUsuario, String nombre, String apellido, String correo, String numTelefono,
            String contrasenya, List<Compra> compras) {
        this.codUsuario = codUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.numTelefono = numTelefono;
        this.contrasenya = contrasenya;
        this.compras = compras;
    }


    public Long getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Long codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    
}
