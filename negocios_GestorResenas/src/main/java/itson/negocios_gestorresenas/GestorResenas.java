/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.negocios_gestorresenas;

import bos.ResenaBO;
import dto.ResenaDTO;
import java.util.List;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class GestorResenas implements IGestorResenas {
    
     private final ResenaBO resenaBO;

    public GestorResenas() {
        this.resenaBO = ResenaBO.getInstancia();
    }

    @Override
    public ResenaDTO agregar(ResenaDTO resenaDTO) {
        return resenaBO.agregar(resenaDTO);
    }

    @Override
    public List<ResenaDTO> obtenerPorBarberia(String idBarberia) {
        return resenaBO.obtenerPorBarberia(idBarberia);
    }

    @Override
    public double calcularPromedio(String idBarberia) {
        return resenaBO.calcularPromedio(idBarberia);
    }
}
