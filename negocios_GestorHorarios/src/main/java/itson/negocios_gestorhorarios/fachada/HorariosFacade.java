/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.negocios_gestorhorarios.fachada;

import bos.HorarioBO;
import dto.HorarioDTO;
import interfaces.IHorarioBO;
import java.util.List;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class HorariosFacade implements IHorariosFacade {

    private final IHorarioBO horarioBO;

    public HorariosFacade() {
        this.horarioBO = HorarioBO.getInstancia();
    }

    @Override
    public List<HorarioDTO> obtenerHorariosPorBarberia(String barberiaId) {
        return horarioBO.obtenerHorariosPorBarberia(barberiaId);
    }
}