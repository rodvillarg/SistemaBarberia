/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.mediadores;

import dto.CitaDTO;
import exceptions.BarberiaNoEncontradaException;
import exceptions.CitaConflictoClienteException;
import exceptions.CitaNoEncontradaException;
import exceptions.ClienteNoEncontradoException;
import exceptions.HorarioNoDisponibleException;
import exceptions.ServicioNoEncontradoException;
import itson.negocios_gestorcitas.fachada.CitasFacade;
import itson.negocios_gestorcitas.fachada.ICitasFacade;
import java.util.List;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class CitaMediator implements ICitaMediator {

    private final ICitasFacade facadeCitas;

    public CitaMediator() {
        this.facadeCitas = new CitasFacade();
    }

    @Override
    public CitaDTO agendarCita(CitaDTO cita)
            throws HorarioNoDisponibleException, CitaConflictoClienteException,
                   ClienteNoEncontradoException, BarberiaNoEncontradaException,
                   ServicioNoEncontradoException {
        return facadeCitas.agendarCita(cita);
    }

    @Override
    public List<CitaDTO> obtenerCitasPorCliente(String clienteId) throws ClienteNoEncontradoException {
        return facadeCitas.obtenerCitasPorCliente(clienteId);
    }

    @Override
    public List<CitaDTO> obtenerCitasPorBarberia(String barberiaId) {
        return facadeCitas.obtenerCitasPorBarberia(barberiaId);
    }

    @Override
    public CitaDTO obtenerPorId(String id) throws CitaNoEncontradaException {
        return facadeCitas.obtenerPorId(id);
    }

    @Override
    public List<String> obtenerHorasOcupadas(String barberiaId, String fecha) {
        return facadeCitas.obtenerHorasOcupadas(barberiaId, fecha);
    }

    @Override
    public void cancelarCita(String idCita) {
        facadeCitas.cancelarCita(idCita);
    }
}
