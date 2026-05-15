package interfaces;

import dto.ServicioDTO;
import exceptions.ServicioNoEncontradoException;
import java.util.List;

/**
 * Interfaz de negocio para operaciones con Servicio.
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public interface IServicioBO {

    ServicioDTO registrar(ServicioDTO servicio);

    List<ServicioDTO> obtenerServiciosPorBarberia(String barberiaId);

    ServicioDTO obtenerPorId(String id) throws ServicioNoEncontradoException;
    
    void eliminar(String id);
}
