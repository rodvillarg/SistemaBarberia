/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.negocios_gestorhorarios;

import java.util.List;
import dto.HorarioDTO;

/**
 *
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public interface IGestorHorarios {

    List<HorarioDTO> obtenerHorariosPorBarberia(String barberiaId);
}
