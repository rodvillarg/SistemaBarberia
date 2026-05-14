/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.mediadores;

import dto.ServicioDTO;
import exceptions.ServicioNoEncontradoException;
import itson.negocios_gestorservicios.fachada.IServiciosFacade;
import itson.negocios_gestorservicios.fachada.ServiciosFacade;
import java.util.List;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class ServicioMediator implements IServicioMediator {

    private final IServiciosFacade facadeServicios;

    public ServicioMediator() {
        this.facadeServicios = new ServiciosFacade();
    }
    
    @Override
    public ServicioDTO registrar(ServicioDTO servicio) {
        return facadeServicios.registrar(servicio);
    }

    @Override
    public List<ServicioDTO> obtenerServiciosPorBarberia(String barberiaId) {
        return facadeServicios.obtenerServiciosPorBarberia(barberiaId);
    }

    @Override
    public ServicioDTO obtenerPorId(String id) throws ServicioNoEncontradoException {
        return facadeServicios.obtenerPorId(id);
    }
}
