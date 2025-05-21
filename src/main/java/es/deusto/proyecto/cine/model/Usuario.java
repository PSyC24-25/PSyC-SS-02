package es.deusto.proyecto.cine.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * @file Usuario.java
 * @brief Clase que representa un usuario en el sistema de cine.
 *
 * @details
 * La clase {@code Usuario} mapea la tabla "usuario" en la base de datos y almacena
 * información sobre el nombre, apellido, correo electrónico, número de teléfono,
 * contraseña y las compras realizadas por el usuario.
 *
 * @see Compra
 *
 * @author
 * PSyC_SS_02
 * @version 1.0
 * @since 2025-05-15
 */
@Entity
public class Usuario {
    /** Identificador único del usuario */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codUsuario;
    /** Nombre del usuario */
    private String nombre;
    /** Apellido del usuario */
    private String apellido;
    /** Correo electrónico del usuario */
    private String correo;
    /** Número de teléfono del usuario */
    private String numTelefono;
    /** Contraseña del usuario */
    private String contrasenya;

    /** Lista de compras realizadas por el usuario */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Compra> compras;

    /** Rol del usuario (usuario o administrador) */
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public Usuario() {
    }

    public Usuario(Long codUsuario, String nombre, String apellido, String correo, String numTelefono,
            String contrasenya, List<Compra> compras, Rol rol) {
        this.codUsuario = codUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.numTelefono = numTelefono;
        this.contrasenya = contrasenya;
        this.compras = compras;
        this.rol = rol;
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

}
