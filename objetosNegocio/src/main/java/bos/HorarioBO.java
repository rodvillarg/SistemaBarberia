package bos;

import daos.HorarioDAO;
import interfaces.IHorarioDAO;
import dominio.Horario;
import dto.HorarioDTO;
import interfaces.IHorarioBO;
import java.util.ArrayList;
import java.util.List;
import mappers.HorarioMapper;
import org.bson.types.ObjectId;

/**
 * @author Jesus Rodrigo Villegas - 261186
 */
public class HorarioBO implements IHorarioBO {

    private final IHorarioDAO horarioDAO;
    private final HorarioMapper mapper;


    public HorarioBO() {
        this.horarioDAO = new HorarioDAO();
        this.mapper     = new HorarioMapper();
    }

    /**
     *
     * @param dto
     * @return
     */
    @Override
    public HorarioDTO registrar(HorarioDTO dto) {
        Horario h = mapper.toEntity(dto);
        h = horarioDAO.insertar(h);
        return mapper.toDTO(h);
    }

    /**
     * Retorna todos los horarios registrados para una barberia.
     *
     * @param barberiaId ID de la barberia a consultar.
     * @return Lista de HorarioDTO con los dias y horas de atencion.
     */
    @Override
    public List<HorarioDTO> obtenerHorariosPorBarberia(String barberiaId) {
        List<Horario> entidades = horarioDAO.buscarPorBarberia(new ObjectId(barberiaId));
        List<HorarioDTO> dtos = new ArrayList<>();
        for (Horario horario : entidades) {
            dtos.add(mapper.toDTO(horario));
        }
        return dtos;
    }

    public boolean estaDisponible(HorarioDTO horario, String horaConsulta) {
        if (horario.getHoraApertura() == null || horario.getHoraCierre() == null) return false;
        return horaConsulta.compareTo(horario.getHoraApertura()) >= 0
                && horaConsulta.compareTo(horario.getHoraCierre()) < 0;
    }
    
    @Override
    public void actualizarHorarios(String barberiaId, List<HorarioDTO> horarios) {
        horarioDAO.eliminarPorBarberia(new ObjectId(barberiaId));
        for (HorarioDTO dto : horarios) {
            Horario h = mapper.toEntity(dto);
            horarioDAO.insertar(h);
        }
    }
}