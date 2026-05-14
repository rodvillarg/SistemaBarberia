/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.mediadores;

import dto.HorarioDTO;
import itson.negocios_gestorhorarios.fachada.HorariosFacade;
import itson.negocios_gestorhorarios.fachada.IHorariosFacade;
import java.util.List;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class HorarioMediator implements IHorarioMediator {

    private final IHorariosFacade facadeHorarios;

    public HorarioMediator() {
        this.facadeHorarios = new HorariosFacade();
    }
    
    @Override
    public HorarioDTO registrar(HorarioDTO horario) {
        return facadeHorarios.registrar(horario);
    }

    @Override
    public List<HorarioDTO> obtenerHorariosPorBarberia(String barberiaId) {
        return facadeHorarios.obtenerHorariosPorBarberia(barberiaId);
    }
}