/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.mediadores;

import dto.ResenaDTO;
import itson.negocios_gestorresenas.fachada.IResenasFacade;
import itson.negocios_gestorresenas.fachada.ResenasFacade;
import java.util.List;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class ResenaMediator implements IResenaMediator {

    private final IResenasFacade facadeResenas;

    public ResenaMediator() {
        this.facadeResenas = new ResenasFacade();
    }

    @Override
    public ResenaDTO agregar(ResenaDTO resena) {
        return facadeResenas.agregar(resena);
    }

    @Override
    public List<ResenaDTO> obtenerPorBarberia(String idBarberia) {
        return facadeResenas.obtenerPorBarberia(idBarberia);
    }

    @Override
    public double calcularPromedio(String idBarberia) {
        return facadeResenas.calcularPromedio(idBarberia);
    }
}
