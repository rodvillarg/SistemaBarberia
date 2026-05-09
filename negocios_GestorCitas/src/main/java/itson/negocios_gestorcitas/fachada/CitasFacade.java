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
import itson.negocios_gestorcitas.GestorCitas;
import itson.negocios_gestorcitas.IGestorCitas;
import java.util.List;

/**
 * 
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class CitasFacade implements ICitasFacade {

    private final IGestorCitas gestorCitas;

    public CitasFacade() {
        this.gestorCitas = new GestorCitas();
    }

    @Override
    public CitaDTO agendarCita(CitaDTO cita)
            throws HorarioNoDisponibleException, CitaConflictoClienteException,
                   ClienteNoEncontradoException, BarberiaNoEncontradaException,
                   ServicioNoEncontradoException {
        return gestorCitas.agendarCita(cita);
    }

    @Override
    public List<CitaDTO> obtenerCitasPorCliente(String clienteId) throws ClienteNoEncontradoException {
        return gestorCitas.obtenerCitasPorCliente(clienteId);
    }

    @Override
    public List<CitaDTO> obtenerCitasPorBarberia(String barberiaId) {
        return gestorCitas.obtenerCitasPorBarberia(barberiaId);
    }

    @Override
    public CitaDTO obtenerPorId(String id) throws CitaNoEncontradaException {
        return gestorCitas.obtenerPorId(id);
    }

    @Override
    public List<String> obtenerHorasOcupadas(String barberiaId, String fecha) {
        return gestorCitas.obtenerHorasOcupadas(barberiaId, fecha);
    }

    @Override
    public void cancelarCita(String idCita) {
        gestorCitas.cancelarCita(idCita);
    }
}
