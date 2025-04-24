package es.deusto.proyecto.cine.dto;

import es.deusto.proyecto.cine.model.Rol;

public class UsuarioDTO {
    private Long codUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String numTelefono;
    private String contrasenya;
    private Rol rol;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long codUsuario, String nombre, String apellido, String correo, String numTelefono, String contrasenya, Rol rol) {
        this.codUsuario = codUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.numTelefono = numTelefono;
        this.contrasenya = contrasenya;
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

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
}
