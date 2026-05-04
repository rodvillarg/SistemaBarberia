package dto;

import dto.enums.RolUsuario;

/**
 * DTO que representa un cliente/usuario del sistema.
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class ClienteDTO {

    private String id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String usuario;
    private String contrasena;
    private RolUsuario rol;

    public ClienteDTO() {
    }

    public ClienteDTO(String nombre, String apellido, String correo,
            String telefono, String usuario, String contrasena, RolUsuario rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public RolUsuario getRol() {
        return rol;
    }
    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }


    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    
}
