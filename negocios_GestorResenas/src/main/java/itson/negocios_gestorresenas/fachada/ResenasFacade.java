/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.negocios_gestorresenas.fachada;


import dto.ResenaDTO;
import itson.negocios_gestorresenas.GestorResenas;
import itson.negocios_gestorresenas.IGestorResenas;
import java.util.List;

/**
 *
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class ResenasFacade implements IResenasFacade{
    
     private final IGestorResenas gestorResenas;

    public ResenasFacade() {
        this.gestorResenas = new GestorResenas();
    }

    @Override
    public ResenaDTO agregar(ResenaDTO resena) {
        return gestorResenas.agregar(resena);
    }

    @Override
    public List<ResenaDTO> obtenerPorBarberia(String idBarberia) {
        return gestorResenas.obtenerPorBarberia(idBarberia);
    }

    @Override
    public double calcularPromedio(String idBarberia) {
        return gestorResenas.calcularPromedio(idBarberia);
    }
}
