/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.negocios_gestorhorarios.fachada;

import dto.HorarioDTO;
import java.util.List;

/**
 *
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public interface IHorariosFacade {
    
    List<HorarioDTO> obtenerHorariosPorBarberia(String barberiaId);
    
}
