package dominio;

import org.bson.types.ObjectId;

/**
 * Entidad de dominio Barberia para MongoDB.
 * 
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class Barberia {

    private ObjectId id;
    private String nombre;
    private String direccion;
    private String telefono;
    private ObjectId idBarbero;

    private String descripcion;
    private String rutaLogo;
    private boolean activa;

    public Barberia() {}

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
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public ObjectId getIdBarbero() {
        return idBarbero;
    }
    public void setIdBarbero(ObjectId idBarbero) {
        this.idBarbero = idBarbero;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getRutaLogo() {
        return rutaLogo;
    }
    public void setRutaLogo(String rutaLogo) {
        this.rutaLogo = rutaLogo;
    }
    public boolean isActiva() {
        return activa;
    }
    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
