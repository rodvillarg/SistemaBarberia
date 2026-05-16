package bos;

import daos.CitaDAO;
import interfaces.ICitaDAO;
import interfaces.IBarberiaBO;
import interfaces.IClienteBO;
import interfaces.IServicioBO;
import dominio.Cita;
import dto.BarberiaDTO;
import dto.CitaDTO;
import dto.ClienteDTO;
import dto.ServicioDTO;
import dto.enums.EstadoCita;
import exceptions.BarberiaNoEncontradaException;
import exceptions.CitaNoEncontradaException;
import exceptions.ClienteNoEncontradoException;
import exceptions.CitaConflictoClienteException;
import exceptions.HorarioNoDisponibleException;
import exceptions.ServicioNoEncontradoException;
import exceptions.CitaYaCanceladaException;
import interfaces.ICitaBO;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import mappers.CitaMapper;
import org.bson.types.ObjectId;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class CitaBO implements ICitaBO {

    private final ICitaDAO citaDAO;
    private final IClienteBO clienteBO;
    private final IBarberiaBO barberiaBO;
    private final IServicioBO servicioBO;
    private final CitaMapper citaMapper;

    public CitaBO() {
        this.citaDAO = new CitaDAO();
        this.clienteBO = new ClienteBO();
        this.barberiaBO = new BarberiaBO();
        this.servicioBO = new ServicioBO();
        this.citaMapper = new CitaMapper();
    }

    /**
     * @param citaDTO
     * @return CitaDTO con los datos de la cita agendada
     * @throws exceptions.HorarioNoDisponibleException
     * @throws exceptions.CitaConflictoClienteException
     * @throws exceptions.ClienteNoEncontradoException
     * @throws exceptions.BarberiaNoEncontradaException
     * @throws exceptions.ServicioNoEncontradoException
     */
    @Override
    public CitaDTO agendarCita(CitaDTO citaDTO)
            throws HorarioNoDisponibleException, CitaConflictoClienteException,
            ClienteNoEncontradoException, BarberiaNoEncontradaException,
            ServicioNoEncontradoException {

        ObjectId idCliente, idBarberia, idServicio;
        try {
            idCliente = new ObjectId(citaDTO.getCliente().getId());
            idBarberia = new ObjectId(citaDTO.getBarberia().getId());
            idServicio = new ObjectId(citaDTO.getServicio().getId());
        } catch (IllegalArgumentException e) {
            throw new ClienteNoEncontradoException("Uno de los IDs no tiene un formato valido.");
        }

        ClienteDTO cliente = clienteBO.obtenerPorId(citaDTO.getCliente().getId());
        BarberiaDTO barberia = barberiaBO.obtenerPorId(citaDTO.getBarberia().getId());
        ServicioDTO servicio = servicioBO.obtenerPorId(citaDTO.getServicio().getId());

        // Verificar que no exista otra cita a la misma hora en la misma barberia
        if (citaDAO.existeConflicto(idBarberia, citaDTO.getFechaHora())) {
            throw new HorarioNoDisponibleException(
                    "El horario " + citaDTO.getFechaHora() + " ya esta ocupado. Elige otro.");
        }

        // Verificar que el cliente no tenga ya una cita a la misma hora
        if (citaDAO.existeConflictoCliente(idCliente, citaDTO.getFechaHora())) {
            throw new CitaConflictoClienteException(
                    "Ya tienes una cita agendada a esa hora en otra barberia.");
        }

        Cita entidad = citaMapper.toEntity(citaDTO);
        Cita guardada = citaDAO.insertar(entidad);

        return citaMapper.toDTO(guardada, cliente, barberia, servicio);
    }

    @Override
    public List<CitaDTO> obtenerCitasPorCliente(String clienteId)
            throws ClienteNoEncontradoException {
        if (clienteId == null || clienteId.isBlank()) {
            throw new ClienteNoEncontradoException("El ID del cliente no es valido.");
        }
        ObjectId oid;
        try {
            oid = new ObjectId(clienteId);
        } catch (IllegalArgumentException e) {
            throw new ClienteNoEncontradoException("El ID del cliente no tiene un formato valido.");
        }

        ClienteDTO clienteDTO = clienteBO.obtenerPorId(clienteId);

        List<CitaDTO> resultado = new ArrayList<>();
        for (Cita c : citaDAO.buscarPorCliente(oid)) {
            BarberiaDTO barberia;
            ServicioDTO servicio;
            try {
                barberia = barberiaBO.obtenerPorId(c.getIdBarberia().toHexString());
                servicio = servicioBO.obtenerPorId(c.getIdServicio().toHexString());
            } catch (BarberiaNoEncontradaException | ServicioNoEncontradoException e) {
                continue;
            }
            resultado.add(citaMapper.toDTO(c, clienteDTO, barberia, servicio));
        }

        // Auto-completar citas vencidas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd H:mm");
        LocalDateTime ahora = LocalDateTime.now();
        for (CitaDTO c : resultado) {
            if (c.getEstado() == EstadoCita.CONFIRMADA) {
                LocalDateTime fechaCita = LocalDateTime.parse(c.getFechaHora(), formatter);
                if (fechaCita.isBefore(ahora)) {
                    citaDAO.completarCita(new ObjectId(c.getId()));
                    c.setEstado(EstadoCita.COMPLETADA);
                }
            }
        }

        return resultado;
    }

    @Override
    public CitaDTO obtenerPorId(String id) throws CitaNoEncontradaException {
        if (id == null || id.isBlank()) {
            throw new CitaNoEncontradaException("El ID de la cita no es valido.");
        }
        ObjectId oid;
        try {
            oid = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            throw new CitaNoEncontradaException("El ID de la cita no tiene un formato valido.");
        }
        Cita cita = citaDAO.buscarPorId(oid);
        if (cita == null) {
            throw new CitaNoEncontradaException("Cita no encontrada.");
        }

        ClienteDTO cliente;
        BarberiaDTO barberia;
        ServicioDTO servicio;
        try {
            cliente = clienteBO.obtenerPorId(cita.getIdCliente().toHexString());
            barberia = barberiaBO.obtenerPorId(cita.getIdBarberia().toHexString());
            servicio = servicioBO.obtenerPorId(cita.getIdServicio().toHexString());
        } catch (ClienteNoEncontradoException | BarberiaNoEncontradaException | ServicioNoEncontradoException e) {
            throw new CitaNoEncontradaException("No se pudieron cargar los datos de la cita.");
        }

        return citaMapper.toDTO(cita, cliente, barberia, servicio);
    }

    @Override
    public List<String> obtenerHorasOcupadas(String barberiaId, String fecha) {
        return citaDAO.buscarHorasOcupadas(new ObjectId(barberiaId), fecha);
    }

    @Override
    public void cancelarCita(String idCita) {
        ObjectId oid;
        try {
            oid = new ObjectId(idCita);
        } catch (IllegalArgumentException e) {
            throw new CitaNoEncontradaException("El ID de la cita no tiene un formato valido.");
        }

        Cita cita = citaDAO.buscarPorId(oid);
        if (cita == null) {
            throw new CitaNoEncontradaException("Cita no encontrada.");
        }

        if (cita.getEstado() == EstadoCita.CANCELADA) {
            throw new CitaYaCanceladaException("La cita ya esta cancelada.");
        }

        cita.cancelar();
        citaDAO.cancelarCita(oid);
    }

    public boolean estaPendiente(CitaDTO cita) {
        return cita.getEstado() == dto.enums.EstadoCita.PENDIENTE;
    }

    public boolean estaConfirmada(CitaDTO cita) {
        return cita.getEstado() == dto.enums.EstadoCita.CONFIRMADA;
    }

    public boolean estaCancelada(CitaDTO cita) {
        return cita.getEstado() == dto.enums.EstadoCita.CANCELADA;
    }

    public List<CitaDTO> obtenerCitasPorBarberia(String barberiaId) {
        List<CitaDTO> resultado = new ArrayList<>();
        List<Cita> citas = citaDAO.buscarPorBarberia(new ObjectId(barberiaId));
        for (Cita cita : citas) {
            ClienteDTO cliente;
            BarberiaDTO barberia;
            ServicioDTO servicio;
            try {
                cliente = clienteBO.obtenerPorId(cita.getIdCliente().toHexString());
                barberia = barberiaBO.obtenerPorId(cita.getIdBarberia().toHexString());
                servicio = servicioBO.obtenerPorId(cita.getIdServicio().toHexString());
            } catch (ClienteNoEncontradoException | BarberiaNoEncontradaException | ServicioNoEncontradoException e) {
                continue;
            }
            resultado.add(citaMapper.toDTO(cita, cliente, barberia, servicio));
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd H:mm");
        LocalDateTime ahora = LocalDateTime.now();
        for (CitaDTO c : resultado) {
            if (c.getEstado() == EstadoCita.CONFIRMADA) {
                LocalDateTime fechaCita = LocalDateTime.parse(c.getFechaHora(), formatter);
                if (fechaCita.isBefore(ahora)) {
                    citaDAO.completarCita(new ObjectId(c.getId()));
                    c.setEstado(EstadoCita.COMPLETADA);
                }
            }
        }
        return resultado;
    }
}
