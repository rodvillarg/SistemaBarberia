package dominio;

import org.bson.types.ObjectId;

/**
 * Entidad de dominio Resena para MongoDB.
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class Resena {

    private ObjectId id;
    private ObjectId idCliente;
    private ObjectId idBarberia;
    private int calificacion;
    private String comentario;
    private String fecha;

    public Resena() {}

    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    public ObjectId getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(ObjectId idCliente) {
        this.idCliente = idCliente;
    }
    public ObjectId getIdBarberia() {
        return idBarberia;
    }
    public void setIdBarberia(ObjectId idBarberia) {
        this.idBarberia = idBarberia;
    }
    public int getCalificacion() {
        return calificacion;
    }
    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean calificacionValida() {
        return calificacion >= 1 && calificacion <= 5;
    }
}
