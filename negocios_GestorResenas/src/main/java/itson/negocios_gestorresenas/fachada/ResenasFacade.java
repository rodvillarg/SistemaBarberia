/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.negocios_gestorresenas.fachada;

import bos.ResenaBO;
import dto.ResenaDTO;
import interfaces.IResenaBO;
import java.util.List;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class ResenasFacade implements IResenasFacade {

    private final IResenaBO resenaBO;

    public ResenasFacade() {
        this.resenaBO = ResenaBO.getInstancia();
    }

    @Override
    public ResenaDTO agregar(ResenaDTO resena) {
        return resenaBO.agregar(resena);
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
