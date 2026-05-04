package dto;

import dto.enums.EstadoPago;
import dto.enums.MetodoPago;

/**
 * DTO para Pago.
 * 
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class PagoDTO {

    private String id;
    private String idCita;
    private double monto;
    private MetodoPago metodoPago;
    private EstadoPago estado;
    private String fecha;

    public PagoDTO(){
        this.estado = EstadoPago.PENDIENTE;
    }

    public PagoDTO(String idCita, double monto, MetodoPago metodoPago, String fecha) {
        this.idCita     = idCita;
        this.monto      = monto;
        this.metodoPago = metodoPago;
        this.fecha      = fecha;
        this.estado     = EstadoPago.PENDIENTE;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIdCita() {
        return idCita;
    }
    public void setIdCita(String idCita) {
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
