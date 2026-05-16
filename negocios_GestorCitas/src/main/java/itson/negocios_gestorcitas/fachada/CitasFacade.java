/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.negocios_gestorcitas.fachada;

import dto.CitaDTO;
import exceptions.BarberiaNoEncontradaException;
import exceptions.CitaConflictoClienteException;
import exceptions.CitaNoEncontradaException;
import exceptions.ClienteNoEncontradoException;
import exceptions.HorarioNoDisponibleException;
import exceptions.ServicioNoEncontradoException;
import bos.CitaBO;
import interfaces.ICitaBO;
import itson.negocios_correoelectronico.ConfiguracionCorreoElectronico;
import itson.negocios_correoelectronico.CorreoElectronico;
import itson.negocios_correoelectronico.ICorreoElectronico;
import java.util.List;

/**
 * 
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class CitasFacade implements ICitasFacade {

    private final ICitaBO citaBO;
    private final ICorreoElectronico correoElectronico;

    public CitasFacade() {
        this.citaBO = new CitaBO();
        this.correoElectronico = new CorreoElectronico(new ConfiguracionCorreoElectronico());
    }

    @Override
    public CitaDTO agendarCita(CitaDTO cita)
            throws HorarioNoDisponibleException, CitaConflictoClienteException,
            ClienteNoEncontradoException, BarberiaNoEncontradaException,
            ServicioNoEncontradoException {

        CitaDTO citaGuardada = citaBO.agendarCita(cita);

        new Thread(() -> {
            try {
                String correoDestino = citaGuardada.getCliente().getCorreo();
                String asunto = "Confirmacion de cita - " + citaGuardada.getBarberia().getNombre();
                String html = construirHtmlConfirmacion(citaGuardada);
                correoElectronico.enviarConfirmacionCita(correoDestino, asunto, html);
            } catch (Exception e) {
                System.out.println("[CitasFacade] Advertencia: no se pudo enviar correo. " + e.getMessage());
            }
        }).start();

        return citaGuardada;
    }

    @Override
    public List<CitaDTO> obtenerCitasPorCliente(String clienteId) throws ClienteNoEncontradoException {
        return citaBO.obtenerCitasPorCliente(clienteId);
    }

    @Override
    public List<CitaDTO> obtenerCitasPorBarberia(String barberiaId) {
        return citaBO.obtenerCitasPorBarberia(barberiaId);
    }

    @Override
    public CitaDTO obtenerPorId(String id) throws CitaNoEncontradaException {
        return citaBO.obtenerPorId(id);
    }

    @Override
    public List<String> obtenerHorasOcupadas(String barberiaId, String fecha) {
        return citaBO.obtenerHorasOcupadas(barberiaId, fecha);
    }

    @Override
    public void cancelarCita(String idCita) {
        citaBO.cancelarCita(idCita);
    }
    
    private String construirHtmlConfirmacion(CitaDTO cita) {
        return "<html><body style='font-family:Arial,sans-serif;color:#222;'>"
                + "<h2 style='color:#1a1a2e;'>Tu cita esta confirmada!</h2>"
                + "<p>Hola, <strong>" + cita.getCliente().getNombreCompleto() + "</strong>.</p>"
                + "<p>Tu cita ha sido agendada exitosamente. Aqui estan los detalles:</p>"
                + "<table style='border-collapse:collapse;width:100%;max-width:500px;'>"
                + "<tr><td style='padding:8px;font-weight:bold;'>Barberia:</td>"
                + "    <td style='padding:8px;'>" + cita.getBarberia().getNombre() + "</td></tr>"
                + "<tr style='background:#f5f5f5;'><td style='padding:8px;font-weight:bold;'>Direccion:</td>"
                + "    <td style='padding:8px;'>" + cita.getBarberia().getDireccion() + "</td></tr>"
                + "<tr><td style='padding:8px;font-weight:bold;'>Servicio:</td>"
                + "    <td style='padding:8px;'>" + cita.getServicio().getNombre() + "</td></tr>"
                + "<tr style='background:#f5f5f5;'><td style='padding:8px;font-weight:bold;'>Fecha y hora:</td>"
                + "    <td style='padding:8px;'>" + cita.getFechaHora() + "</td></tr>"
                + "</table>"
                + "</body></html>";
    }
}
