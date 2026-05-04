package dto;

/**
 * DTO para Servicio. 
 * @author Jesus Rodrigo Villegas - 261186
 */
public class ServicioDTO {

    private String id;
    private String idBarberia;
    private String nombre;
    private String descripcion;
    private double precio;
    private int duracionMinutos;

    public ServicioDTO() {

    }

    public ServicioDTO(String idBarberia, String nombre, String descripcion,
            double precio, int duracionMinutos) {
        this.idBarberia = idBarberia;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracionMinutos = duracionMinutos;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIdBarberia() {
        return idBarberia;
    }
    public void setIdBarberia(String idBarberia) {
        this.idBarberia = idBarberia;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    @Override
    public String toString() { return "Servicio: " + nombre + "\n Precio: " + precio; }
}
