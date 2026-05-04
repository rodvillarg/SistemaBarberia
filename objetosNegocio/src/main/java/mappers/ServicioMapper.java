package mappers;

import dominio.Servicio;
import dto.ServicioDTO;
import org.bson.types.ObjectId;

/**
 * @author Jesus Rodrigo Villegas - 261186
 */
public class ServicioMapper {

    public ServicioDTO toDTO(Servicio servicio) {
        if (servicio == null) return null;
        ServicioDTO dto = new ServicioDTO();
        dto.setId(servicio.getId().toHexString());
        if (servicio.getIdBarberia() != null)
            dto.setIdBarberia(servicio.getIdBarberia().toHexString());
        dto.setNombre(servicio.getNombre());
        dto.setDescripcion(servicio.getDescripcion());
        dto.setPrecio(servicio.getPrecio());
        dto.setDuracionMinutos(servicio.getDuracionMinutos());
        return dto;
    }

    public Servicio toEntity(ServicioDTO dto) {
        if (dto == null) return null;
        Servicio s = new Servicio();
        if (dto.getId() != null && !dto.getId().isBlank())
            s.setId(new ObjectId(dto.getId()));
        if (dto.getIdBarberia() != null && !dto.getIdBarberia().isBlank())
            s.setIdBarberia(new ObjectId(dto.getIdBarberia()));
        s.setNombre(dto.getNombre());
        s.setDescripcion(dto.getDescripcion());
        s.setPrecio(dto.getPrecio());
        s.setDuracionMinutos(dto.getDuracionMinutos());
        return s;
    }
}
