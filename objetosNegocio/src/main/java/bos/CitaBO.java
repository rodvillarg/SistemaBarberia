package bos;

import daos.BarberiaDAO;
import daos.CitaDAO;
import daos.ClienteDAO;
import daos.ServicioDAO;
import dominio.Barberia;
import dominio.Cita;
import dominio.Cliente;
import dominio.Servicio;
import dto.CitaDTO;
import dto.ClienteDTO;
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
import mappers.BarberiaMapper;
import mappers.CitaMapper;
import mappers.ClienteMapper;
import mappers.ServicioMapper;
import org.bson.types.ObjectId;

/**
 * 
 * @author Jesus Rodrigo Villegas - 261186
 */
public class CitaBO implements ICitaBO {

    private static CitaBO instancia;

    private final CitaDAO        citaDAO;
    private final ClienteDAO     clienteDAO;
    private final BarberiaDAO    barberiaDAO;
    private final ServicioDAO    servicioDAO;
    private final CitaMapper     citaMapper;
    private final ClienteMapper  clienteMapper;
    private final BarberiaMapper barberiaMapper;
    private final ServicioMapper servicioMapper;

    private CitaBO() {
        this.citaDAO        = new CitaDAO();
        this.clienteDAO     = new ClienteDAO();
        this.barberiaDAO    = new BarberiaDAO();
        this.servicioDAO    = new ServicioDAO();
        this.citaMapper     = new CitaMapper();
        this.clienteMapper  = new ClienteMapper();
        this.barberiaMapper = new BarberiaMapper();
        this.servicioMapper = new ServicioMapper();
    }

    public static synchronized CitaBO getInstancia() {
        if (instancia == null) {
            instancia = new CitaBO();
        }
        return instancia;
    }

    /**
     *
     * @param citaDTO
     * @return
     * @throws exceptions.HorarioNoDisponibleException
     * @throws exceptions.CitaConflictoClienteException
     * @throws exceptions.ClienteNoEncontradoException
     * @throws exceptions.BarberiaNoEncontradaException
     * @throws exceptions.ServicioNoEncontradoException
     */
    @Override
    public CitaDTO agendarCita(CitaDTO citaDTO)
            throws HorarioNoDisponibleException, CitaConflictoClienteException,
            ClienteNoEncontradoException,
                   BarberiaNoEncontradaException, ServicioNoEncontradoException {

        ObjectId idCliente, idBarberia, idServicio;
        try {
            idCliente  = new ObjectId(citaDTO.getCliente().getId());
            idBarberia = new ObjectId(citaDTO.getBarberia().getId());
            idServicio = new ObjectId(citaDTO.getServicio().getId());
        } catch (IllegalArgumentException e) {
            throw new ClienteNoEncontradoException("Uno de los IDs no tiene un formato valido.");
        }

        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        if (cliente == null)
            throw new ClienteNoEncontradoException("Cliente no encontrado.");

        Barberia barberia = barberiaDAO.buscarPorId(idBarberia);
        if (barberia == null)
            throw new BarberiaNoEncontradaException("Barberia no encontrada.");

        Servicio servicio = servicioDAO.buscarPorId(idServicio);
        if (servicio == null)
            throw new ServicioNoEncontradoException("Servicio no encontrado.");

        // Verificar que no exista otra cita a la misma hora en la misma barberia
        if (citaDAO.existeConflicto(barberia.getId(), citaDTO.getFechaHora()))
            throw new HorarioNoDisponibleException(
                    "El horario " + citaDTO.getFechaHora() + " ya esta ocupado. Elige otro.");

        // Verificar que el cliente no tenga ya una cita a la misma hora
        if (citaDAO.existeConflictoCliente(cliente.getId(), citaDTO.getFechaHora()))
            throw new CitaConflictoClienteException(
                    "Ya tienes una cita agendada a esa hora en otra barberia.");

        Cita entidad  = citaMapper.toEntity(citaDTO);
        // Cita conflicto = citaDAO.buscarConflicto(barberia.getId(), citaDTO.getFechaHora());
        Cita guardada = citaDAO.insertar(entidad);

        return citaMapper.toDTO(guardada,
                clienteMapper.toDTO(cliente),
                barberiaMapper.toDTO(barberia),
                servicioMapper.toDTO(servicio));
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
        Cliente cliente = clienteDAO.buscarPorId(oid);
        if (cliente == null)
            throw new ClienteNoEncontradoException("Cliente no encontrado.");

        List<CitaDTO> resultado = new ArrayList<>();
        ClienteDTO clienteDTO = clienteMapper.toDTO(cliente);
        for (Cita c : citaDAO.buscarPorCliente(oid)) {
            Barberia barberia = barberiaDAO.buscarPorId(c.getIdBarberia());
            Servicio servicio = servicioDAO.buscarPorId(c.getIdServicio());
            if (barberia == null || servicio == null) {
                continue;
            }
            resultado.add(citaMapper.toDTO(c,
                    clienteDTO,
                    barberiaMapper.toDTO(barberia),
                    servicioMapper.toDTO(servicio)));
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
        if (cita == null)
            throw new CitaNoEncontradaException("Cita no encontrada.");

        Cliente  cliente  = clienteDAO.buscarPorId(cita.getIdCliente());
        Barberia barberia = barberiaDAO.buscarPorId(cita.getIdBarberia());
        Servicio servicio = servicioDAO.buscarPorId(cita.getIdServicio());
        return citaMapper.toDTO(cita,
                clienteMapper.toDTO(cliente),
                barberiaMapper.toDTO(barberia),
                servicioMapper.toDTO(servicio));
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
        if (cita == null)
            throw new CitaNoEncontradaException("Cita no encontrada.");

        if (cita.getEstado() == EstadoCita.CANCELADA)
            throw new CitaYaCanceladaException("La cita ya esta cancelada.");

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
            Cliente  cliente  = clienteDAO.buscarPorId(cita.getIdCliente());
            Barberia barberia = barberiaDAO.buscarPorId(cita.getIdBarberia());
            Servicio servicio = servicioDAO.buscarPorId(cita.getIdServicio());
            resultado.add(citaMapper.toDTO(cita,
                    clienteMapper.toDTO(cliente),
                    barberiaMapper.toDTO(barberia),
                    servicioMapper.toDTO(servicio)));
        }
        return resultado;
    }
}
