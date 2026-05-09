/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.negocios_gestorresenas;

import dto.ResenaDTO;
import java.util.List;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public interface IGestorResenas {
    
    ResenaDTO agregar(ResenaDTO resenaDTO);
    List<ResenaDTO> obtenerPorBarberia(String idBarberia);
    double calcularPromedio(String idBarberia);
}
