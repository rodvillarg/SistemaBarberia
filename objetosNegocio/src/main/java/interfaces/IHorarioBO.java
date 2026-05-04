package interfaces;

import dto.HorarioDTO;
import java.util.List;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public interface IHorarioBO {

    List<HorarioDTO> obtenerHorariosPorBarberia(String barberiaId);

    HorarioDTO registrar(HorarioDTO horario);
}
