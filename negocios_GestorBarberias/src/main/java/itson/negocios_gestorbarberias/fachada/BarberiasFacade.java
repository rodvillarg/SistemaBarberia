/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.negocios_gestorbarberias.fachada;

import dto.BarberiaDTO;
import exceptions.BarberiaNoEncontradaException;
import itson.negocios_gestorbarberias.GestorBarberias;
import itson.negocios_gestorbarberias.IGestorBarberias;
import java.util.List;
/**
 *
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class BarberiasFacade implements IBarberiasFacade{
    
    private final IGestorBarberias gestorBarberias;

    public BarberiasFacade() {
        this.gestorBarberias = new GestorBarberias();
    }

    @Override
    public BarberiaDTO registrar(BarberiaDTO barberia) {
        return gestorBarberias.registrar(barberia);
    }

    @Override
    public List<BarberiaDTO> obtenerBarberiasActivas() {
        return gestorBarberias.obtenerBarberiasActivas();
    }

    @Override
    public BarberiaDTO obtenerPorId(String id) throws BarberiaNoEncontradaException {
        return gestorBarberias.obtenerPorId(id);
    }

    @Override
    public BarberiaDTO obtenerPorNombre(String nombre) throws BarberiaNoEncontradaException {
        return gestorBarberias.obtenerPorNombre(nombre);
    }
}
