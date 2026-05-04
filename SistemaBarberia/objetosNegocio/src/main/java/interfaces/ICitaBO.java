package interfaces;

import dto.CitaDTO;
import exceptions.BarberiaNoEncontradaException;
import exceptions.CitaNoEncontradaException;
import exceptions.ClienteNoEncontradoException;
import exceptions.CitaConflictoClienteException;
import exceptions.HorarioNoDisponibleException;
import exceptions.ServicioNoEncontradoException;
import java.util.List;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public interface ICitaBO {

    CitaDTO agendarCita(CitaDTO cita) throws HorarioNoDisponibleException, CitaConflictoClienteException,
            ClienteNoEncontradoException, BarberiaNoEncontradaException,
            ServicioNoEncontradoException;

    List<CitaDTO> obtenerCitasPorCliente(String clienteId) throws ClienteNoEncontradoException;

    List<CitaDTO> obtenerCitasPorBarberia(String barberiaId);

    void cancelarCita(String idCita);

    CitaDTO obtenerPorId(String id) throws CitaNoEncontradaException;

    List<String> obtenerHorasOcupadas(String barberiaId, String fecha);
}
