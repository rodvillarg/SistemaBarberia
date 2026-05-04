package dominio;

import org.bson.types.ObjectId;

/**
 * Entidad de dominio Servicio para MongoDB.
 * 
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class Servicio {

    private ObjectId id;
    private ObjectId idBarberia;
    private String nombre;
    private String descripcion;
    private double precio;
    private int duracionMinutos;

    public Servicio() {}

    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    public ObjectId getIdBarberia() {
        return idBarberia;
    }
    public void setIdBarberia(ObjectId idBarberia) {
        this.idBarberia = idBarberia;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public int getDuracionMinutos() {
        return duracionMinutos;
    }
    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }
}
