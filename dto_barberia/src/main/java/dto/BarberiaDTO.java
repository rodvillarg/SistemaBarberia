package dto;

/**
 * DTO para Barberia.
 * 
 *
 * 
 * 
 * prueba
 * @author Jesus Rodrigo Villegas - 261186
 */
public class BarberiaDTO {


    private String id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String idBarbero;

    private String descripcion;
    private String rutaLogo;
    private boolean activa;

    public BarberiaDTO(){
    }

    public BarberiaDTO(String nombre, String direccion, String telefono, String idBarbero,
            String descripcion) {
        this.nombre               = nombre;
        this.direccion            = direccion;
        this.telefono             = telefono;
        this.idBarbero            = idBarbero;
        this.descripcion          = descripcion;
        this.activa               = true;
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
    public String getIdBarbero() {
        return idBarbero;
    }
    public void setIdBarbero(String idBarbero) {
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


    @Override
    public String toString() { return "Barberia: " + nombre + "\n Direccion: " + direccion; }
}
