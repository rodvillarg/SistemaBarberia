/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.negocios_gestorbarberias;

import bos.BarberiaBO;
import bos.HorarioBO;
import dto.BarberiaDTO;
import dto.HorarioDTO;
import exceptions.BarberiaNoEncontradaException;
import interfaces.IBarberiaBO;
import interfaces.IHorarioBO;
import java.util.List;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class GestorBarberias implements IGestorBarberias{
     private final IBarberiaBO barberiaBO;

    public GestorBarberias() {
        this.barberiaBO = BarberiaBO.getInstancia();
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
}