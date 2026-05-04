package mappers;

import dominio.Barberia;
import dto.BarberiaDTO;
import org.bson.types.ObjectId;

/**
 * @author Jesus Rodrigo Villegas - 261186
 */
public class BarberiaMapper {

    public BarberiaDTO toDTO(Barberia b) {
        if (b == null) {
            return null;
        }
        BarberiaDTO dto = new BarberiaDTO();
        dto.setId(b.getId().toHexString());
        dto.setNombre(b.getNombre());
        dto.setDireccion(b.getDireccion());
        dto.setTelefono(b.getTelefono());
        if (b.getIdBarbero() != null) {
            dto.setIdBarbero(b.getIdBarbero().toHexString());
        }
        dto.setDescripcion(b.getDescripcion());
        dto.setRutaLogo(b.getRutaLogo());
        dto.setActiva(b.isActiva());
        return dto;
    }

    public Barberia toEntity(BarberiaDTO barberiaDTO) {
        if (barberiaDTO == null) {
            return null;
        }
        Barberia b = new Barberia();
        if (barberiaDTO.getId() != null && !barberiaDTO.getId().isBlank()) {
            try {
                b.setId(new ObjectId(barberiaDTO.getId()));
            } catch (IllegalArgumentException e) {
            }
        }
        b.setNombre(barberiaDTO.getNombre());
        b.setDireccion(barberiaDTO.getDireccion());
        b.setTelefono(barberiaDTO.getTelefono());
        if (barberiaDTO.getIdBarbero() != null && !barberiaDTO.getIdBarbero().isBlank()) {
            b.setIdBarbero(new ObjectId(barberiaDTO.getIdBarbero()));
        }
        b.setDescripcion(barberiaDTO.getDescripcion());
        b.setRutaLogo(barberiaDTO.getRutaLogo());
        b.setActiva(barberiaDTO.isActiva());
        return b;
    }
}
