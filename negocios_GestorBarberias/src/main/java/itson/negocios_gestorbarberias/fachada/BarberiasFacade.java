/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.negocios_gestorbarberias.fachada;

import bos.BarberiaBO;
import dto.BarberiaDTO;
import exceptions.BarberiaNoEncontradaException;
import interfaces.IBarberiaBO;
import java.util.List;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class BarberiasFacade implements IBarberiasFacade {

    private final IBarberiaBO barberiaBO;

    public BarberiasFacade() {
        this.barberiaBO = new BarberiaBO();
    }

    @Override
    public BarberiaDTO registrar(BarberiaDTO barberia) {
        return barberiaBO.registrar(barberia);
    }

    @Override
    public List<BarberiaDTO> obtenerBarberiasActivas() {
        return barberiaBO.obtenerBarberiasActivas();
    }

    @Override
    public BarberiaDTO obtenerPorId(String id) throws BarberiaNoEncontradaException {
        return barberiaBO.obtenerPorId(id);
    }

    @Override
    public BarberiaDTO obtenerPorNombre(String nombre) throws BarberiaNoEncontradaException {
        return barberiaBO.obtenerPorNombre(nombre);
    }
    
    @Override
    public BarberiaDTO obtenerPorBarbero(String idBarbero) {
        return barberiaBO.obtenerPorBarbero(idBarbero);
    }
}