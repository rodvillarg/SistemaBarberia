package mappers;

import dominio.Horario;
import dto.HorarioDTO;
import org.bson.types.ObjectId;

/**
 * @author Jesus Rodrigo Villegas - 261186
 */
public class HorarioMapper {

    public HorarioDTO toDTO(Horario horario) {
        if (horario == null) {
            return null;
        }
        HorarioDTO dto = new HorarioDTO();
        dto.setId(horario.getId().toHexString());
        if (horario.getIdBarberia() != null) {
            dto.setIdBarberia(horario.getIdBarberia().toHexString());
        }
        dto.setDiaSemana(horario.getDiaSemana());
        dto.setHoraApertura(horario.getHoraApertura());
        dto.setHoraCierre(horario.getHoraCierre());
        return dto;
    }

    public Horario toEntity(HorarioDTO horarioDTO) {
        if (horarioDTO == null) {
            return null;
        }
        Horario h = new Horario();
        if (horarioDTO.getId() != null && !horarioDTO.getId().isBlank()) {
            h.setId(new ObjectId(horarioDTO.getId()));
        }
        if (horarioDTO.getIdBarberia() != null && !horarioDTO.getIdBarberia().isBlank()) {
            h.setIdBarberia(new ObjectId(horarioDTO.getIdBarberia()));
        }
        h.setDiaSemana(horarioDTO.getDiaSemana());
        h.setHoraApertura(horarioDTO.getHoraApertura());
        h.setHoraCierre(horarioDTO.getHoraCierre());
        return h;
    }
}
