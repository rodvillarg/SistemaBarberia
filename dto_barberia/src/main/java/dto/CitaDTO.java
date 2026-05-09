package dto;

import dto.enums.EstadoCita;

/**
 * DTO para Cita.
 * fechaHora en formato String "YYYY/MM/DD H:mm".
 * @author Jesus Rodrigo Villegas - 261186
 */
public class CitaDTO {

    private String id;
    private ClienteDTO cliente;
    private BarberiaDTO barberia;
    private ServicioDTO servicio;
    private String fechaHora;   // Formato: YYYY/MM/DD H:mm
    private EstadoCita estado;
    private dto.enums.MetodoPago metodoPago;

    public CitaDTO() {
    }

    public CitaDTO(ClienteDTO cliente, BarberiaDTO barberia,
            ServicioDTO servicio, String fechaHora) {
        this.cliente   = cliente;
        this.barberia  = barberia;
        this.servicio  = servicio;
        this.fechaHora = fechaHora;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public ClienteDTO getCliente() {
        return cliente;
    }
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
    public BarberiaDTO getBarberia() {
        return barberia;
    }
    public void setBarberia(BarberiaDTO barberia) {
        this.barberia = barberia;
    }
    public ServicioDTO getServicio() {
        return servicio;
    }
    public void setServicio(ServicioDTO servicio) {
        this.servicio = servicio;
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
    public dto.enums.MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(dto.enums.MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }


    @Override
    public String toString() {
        return "Cita #" + id + " | Fecha: " + fechaHora + " | Estado: " + estado
                + " | Servicio: " + (servicio != null ? servicio.getNombre() : "N/A");
    }
    
    
}