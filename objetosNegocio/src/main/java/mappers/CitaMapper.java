package mappers;

import dominio.Cita;
import dto.BarberiaDTO;
import dto.CitaDTO;
import dto.ClienteDTO;
import dto.ServicioDTO;
import org.bson.types.ObjectId;

/**
 * @author Jesus Rodrigo Villegas - 261186
 */
public class CitaMapper {

    public CitaDTO toDTO(Cita cita, ClienteDTO clienteDTO,
            BarberiaDTO barberiaDTO, ServicioDTO servicioDTO) {
        if (cita == null) return null;
        CitaDTO dto = new CitaDTO();
        dto.setId(cita.getId().toHexString());
        dto.setCliente(clienteDTO);
        dto.setBarberia(barberiaDTO);
        dto.setServicio(servicioDTO);
        dto.setFechaHora(cita.getFechaHora());
        dto.setEstado(cita.getEstado());
        dto.setMetodoPago(cita.getMetodoPago());
        return dto;
    }

    public Cita toEntity(CitaDTO dto) {
        if (dto == null) return null;
        Cita cita = new Cita();
        if (dto.getId() != null && !dto.getId().isBlank())
            cita.setId(new ObjectId(dto.getId()));
        if (dto.getCliente() != null && dto.getCliente().getId() != null)
            cita.setIdCliente(new ObjectId(dto.getCliente().getId()));
        if (dto.getBarberia() != null && dto.getBarberia().getId() != null)
            cita.setIdBarberia(new ObjectId(dto.getBarberia().getId()));
        if (dto.getServicio() != null && dto.getServicio().getId() != null)
            cita.setIdServicio(new ObjectId(dto.getServicio().getId()));
        cita.setFechaHora(dto.getFechaHora());
        if (dto.getEstado() != null) {
            cita.setEstado(dto.getEstado());
        }
        if (dto.getMetodoPago() != null)
            cita.setMetodoPago(dto.getMetodoPago());
        return cita;
    }
}
