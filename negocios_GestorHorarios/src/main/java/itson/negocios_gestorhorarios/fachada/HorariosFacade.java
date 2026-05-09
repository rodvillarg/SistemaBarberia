/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.negocios_gestorhorarios.fachada;

import dto.HorarioDTO;
import itson.negocios_gestorhorarios.GestorHorarios;
import itson.negocios_gestorhorarios.IGestorHorarios;
import java.util.List;
/**
 *
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class HorariosFacade implements IHorariosFacade{
    
    private final IGestorHorarios gestorHorarios;

    public HorariosFacade() {
        this.gestorHorarios = new GestorHorarios();
    }

    @Override
    public List<HorarioDTO> obtenerHorariosPorBarberia(String barberiaId) {
        return gestorHorarios.obtenerHorariosPorBarberia(barberiaId);
    }
}
