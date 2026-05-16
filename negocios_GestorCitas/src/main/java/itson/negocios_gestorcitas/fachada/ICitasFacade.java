/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.negocios_gestorcitas.fachada;

import dto.CitaDTO;
import exceptions.BarberiaNoEncontradaException;
import exceptions.CitaConflictoClienteException;
import exceptions.CitaNoEncontradaException;
import exceptions.ClienteNoEncontradoException;
import exceptions.HorarioNoDisponibleException;
import exceptions.ServicioNoEncontradoException;
import java.util.List;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public interface ICitasFacade {

    CitaDTO agendarCita(CitaDTO cita)
            throws HorarioNoDisponibleException,
            CitaConflictoClienteException,
            ClienteNoEncontradoException,
            BarberiaNoEncontradaException,
            ServicioNoEncontradoException;

    List<CitaDTO> obtenerCitasPorCliente(String clienteId)
            throws ClienteNoEncontradoException;

    List<CitaDTO> obtenerCitasPorBarberia(String barberiaId);

    CitaDTO obtenerPorId(String id)
            throws CitaNoEncontradaException;

    List<String> obtenerHorasOcupadas(String barberiaId, String fecha);

    void cancelarCita(String idCita);
}
