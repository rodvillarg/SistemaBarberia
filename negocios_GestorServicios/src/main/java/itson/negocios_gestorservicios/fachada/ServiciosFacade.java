/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.negocios_gestorservicios.fachada;

import dto.ServicioDTO;
import exceptions.ServicioNoEncontradoException;
import itson.negocios_gestorservicios.GestorServicios;
import itson.negocios_gestorservicios.IGestorServicios;
import java.util.List;
/**
 *
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class ServiciosFacade implements IServiciosFacade {
    
    private final IGestorServicios gestorServicios;

    public ServiciosFacade() {
        this.gestorServicios = new GestorServicios();
    }

    @Override
    public List<ServicioDTO> obtenerServiciosPorBarberia(String barberiaId) {
        return gestorServicios.obtenerServiciosPorBarberia(barberiaId);
    }

    @Override
    public ServicioDTO obtenerPorId(String id) throws ServicioNoEncontradoException {
        return gestorServicios.obtenerPorId(id);
    }
}
