/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package itson.negocios_gestorhorarios;
import bos.HorarioBO;
import dto.HorarioDTO;
import interfaces.IHorarioBO;
import java.util.List;

/**
 *
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class GestorHorarios implements IGestorHorarios {

    private final IHorarioBO horarioBO;

    public GestorHorarios() {
        this.horarioBO = HorarioBO.getInstancia();
    }

    @Override
    public List<HorarioDTO> obtenerHorariosPorBarberia(String barberiaId) {
        return horarioBO.obtenerHorariosPorBarberia(barberiaId);
    }
}
