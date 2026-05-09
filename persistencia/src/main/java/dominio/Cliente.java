package dominio;

import dto.enums.RolUsuario;
import org.bson.types.ObjectId;

/**
 * Entidad de dominio que representa un cliente/usuario en la base de datos.
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class Cliente {

    private ObjectId id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String usuario;
    private String contrasena;
    private RolUsuario rol;

    public Cliente() {
    }

    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
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
    
    
}
