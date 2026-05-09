package dominio;

import dto.enums.EstadoPago;
import dto.enums.MetodoPago;
import org.bson.types.ObjectId;

/**
 * Entidad de dominio Pago para MongoDB.
 * 
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class Pago {

    private ObjectId id;
    private ObjectId idCita;
    private double monto;
    private MetodoPago metodoPago;
    private EstadoPago estado;
    private String fecha;

    public Pago() {
        this.estado = EstadoPago.PENDIENTE;
    }

    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    public ObjectId getIdCita() {
        return idCita;
    }
    public void setIdCita(ObjectId idCita) {
        this.idCita = idCita;
    }
    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
    public MetodoPago getMetodoPago() {
        return metodoPago;
    }
    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }
    public EstadoPago getEstado() {
        return estado;
    }
    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
}
