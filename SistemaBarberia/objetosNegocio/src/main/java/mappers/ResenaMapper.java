package mappers;

import dominio.Resena;
import dto.ResenaDTO;
import org.bson.types.ObjectId;

/**
 * @author Jesus Rodrigo Villegas - 261186
 */
public class ResenaMapper {

    public ResenaMapper() {
    }

    public ResenaDTO toDTO(Resena resena) {
        if (resena == null) {
            return null;
        }

        ResenaDTO dto = new ResenaDTO();

        dto.setId(resena.getId().toHexString());
        dto.setCalificacion(resena.getCalificacion());
        dto.setComentario(resena.getComentario());
        dto.setFecha(resena.getFecha());

        if (resena.getIdCliente() != null)
            dto.setIdCliente(resena.getIdCliente().toHexString());

        if (resena.getIdBarberia() != null)
            dto.setIdBarberia(resena.getIdBarberia().toHexString());

        return dto;
    }

    public Resena toEntity(ResenaDTO dto) {
        if (dto == null) {
            return null;
        }

        Resena resena = new Resena();

        if (dto.getId() != null && !dto.getId().isBlank()) {
            try {
                resena.setId(new ObjectId(dto.getId()));
            } catch (IllegalArgumentException e) {

            }
        }

        resena.setCalificacion(dto.getCalificacion());
        resena.setComentario(dto.getComentario());
        resena.setFecha(dto.getFecha());

        if (dto.getIdCliente() != null && !dto.getIdCliente().isBlank())
            resena.setIdCliente(new ObjectId(dto.getIdCliente()));

        if (dto.getIdBarberia() != null && !dto.getIdBarberia().isBlank())
            resena.setIdBarberia(new ObjectId(dto.getIdBarberia()));

        return resena;
    }
}
