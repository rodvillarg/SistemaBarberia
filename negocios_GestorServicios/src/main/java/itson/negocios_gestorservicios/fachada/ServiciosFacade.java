/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.negocios_gestorservicios.fachada;

import bos.ServicioBO;
import dto.ServicioDTO;
import exceptions.ServicioNoEncontradoException;
import interfaces.IServicioBO;
import java.util.List;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class ServiciosFacade implements IServiciosFacade {

    private final IServicioBO servicioBO;

    public ServiciosFacade() {
        this.servicioBO = ServicioBO.getInstancia();
    }

    @Override
    public List<ServicioDTO> obtenerServiciosPorBarberia(String barberiaId) {
        return servicioBO.obtenerServiciosPorBarberia(barberiaId);
    }

    @Override
    public ServicioDTO obtenerPorId(String id) throws ServicioNoEncontradoException {
        return servicioBO.obtenerPorId(id);
    }
}