/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.mediadores;

import dto.BarberiaDTO;
import exceptions.BarberiaNoEncontradaException;
import itson.negocios_gestorbarberias.fachada.BarberiasFacade;
import itson.negocios_gestorbarberias.fachada.IBarberiasFacade;
import java.util.List;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class BarberiaMediator implements IBarberiaMediator {

    private final IBarberiasFacade facadeBarberias;

    public BarberiaMediator() {
        this.facadeBarberias = new BarberiasFacade();
    }

    @Override
    public BarberiaDTO registrar(BarberiaDTO barberia) {
        return facadeBarberias.registrar(barberia);
    }

    @Override
    public List<BarberiaDTO> obtenerBarberiasActivas() {
        return facadeBarberias.obtenerBarberiasActivas();
    }

    @Override
    public BarberiaDTO obtenerPorId(String id) throws BarberiaNoEncontradaException {
        return facadeBarberias.obtenerPorId(id);
    }

    @Override
    public BarberiaDTO obtenerPorNombre(String nombre) throws BarberiaNoEncontradaException {
        return facadeBarberias.obtenerPorNombre(nombre);
    }
    
    @Override
    public BarberiaDTO obtenerPorBarbero(String idBarbero) {
        return facadeBarberias.obtenerPorBarbero(idBarbero);
    }
}
