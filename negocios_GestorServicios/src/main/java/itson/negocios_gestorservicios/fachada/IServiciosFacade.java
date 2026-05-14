/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.negocios_gestorservicios.fachada;

import dto.ServicioDTO;
import exceptions.ServicioNoEncontradoException;
import java.util.List;
/**
 *
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public interface IServiciosFacade {
    
    ServicioDTO registrar(ServicioDTO servicio);

    
    List<ServicioDTO> obtenerServiciosPorBarberia(String barberiaId);

    ServicioDTO obtenerPorId(String id) throws ServicioNoEncontradoException;
}
