package es.deusto.proyecto.cine.dto;


public class UsuarioDTO {
    private Long codUsuario;
    private String nombre;
    private String apellido;
    private String correo;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long codUsuario, String nombre, String apellido, String correo) {
        this.codUsuario = codUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
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
}
