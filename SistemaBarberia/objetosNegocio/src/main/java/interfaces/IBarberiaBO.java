package interfaces;

import dto.BarberiaDTO;
import exceptions.BarberiaNoEncontradaException;
import java.util.List;

/**
 * Interfaz de negocio para operaciones con Barberia.
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public interface IBarberiaBO {

    BarberiaDTO registrar(BarberiaDTO barberia);

    void actualizarIdBarbero(String idBarberia, String idBarbero);

    List<BarberiaDTO> obtenerBarberiasActivas();

    BarberiaDTO obtenerPorId(String id) throws BarberiaNoEncontradaException;

    BarberiaDTO obtenerPorNombre(String nombre) throws BarberiaNoEncontradaException;

}
