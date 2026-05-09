package dominio;

import dto.enums.EstadoCita;
import dto.enums.MetodoPago;
import org.bson.types.ObjectId;

/**
 * Entidad de dominio Cita para MongoDB.
 * 
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class Cita {

    private ObjectId id;
    private ObjectId idCliente;
    private ObjectId idBarberia;
    private ObjectId idServicio;
    private String fechaHora;       // Formato: YYYY/MM/DD H:mm
    private EstadoCita estado;
    private MetodoPago metodoPago;

    public Cita() {
        this.estado = EstadoCita.CONFIRMADA;
    }

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
    public ObjectId getIdServicio() {
        return idServicio;
    }
    public void setIdServicio(ObjectId idServicio) {
        this.idServicio = idServicio;
    }
    public String getFechaHora() {
        return fechaHora;
    }
    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
    public EstadoCita getEstado() {
        return estado;
    }
    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }
    public MetodoPago getMetodoPago() {
        return metodoPago;
    }
    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }
    
    public void confirmar() {
        this.estado = EstadoCita.CONFIRMADA;
    }

    public void cancelar() {
        this.estado = EstadoCita.CANCELADA;
    }
    
}
