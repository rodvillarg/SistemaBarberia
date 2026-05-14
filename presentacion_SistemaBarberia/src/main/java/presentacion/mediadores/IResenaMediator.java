/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package presentacion.mediadores;

import dto.ResenaDTO;
import java.util.List;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public interface IResenaMediator {

    ResenaDTO agregar(ResenaDTO resena);

    List<ResenaDTO> obtenerPorBarberia(String idBarberia);

    double calcularPromedio(String idBarberia);
}
