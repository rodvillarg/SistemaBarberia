package dto;

/**
 * DTO para Resena.
 * 
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class ResenaDTO {

    private String id;
    private String idCliente;
    private String idBarberia;
    private int calificacion;   // Valores: 1-5
    private String comentario;
    private String fecha;

    public ResenaDTO(){
    }

    public ResenaDTO(String idCliente, String idBarberia,
            int calificacion, String comentario, String fecha) {
        this.idCliente   = idCliente;
        this.idBarberia  = idBarberia;
        this.calificacion = calificacion;
        this.comentario  = comentario;
        this.fecha       = fecha;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
    public String getIdBarberia() {
        return idBarberia;
    }
    public void setIdBarberia(String idBarberia) {
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


    
}
