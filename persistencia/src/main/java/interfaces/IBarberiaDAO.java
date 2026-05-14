package interfaces;

import dominio.Barberia;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Interfaz DAO para Barberia.
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public interface IBarberiaDAO {

    Barberia insertar(Barberia barberia);

    void actualizarIdBarbero(ObjectId idBarberia, String idBarbero);

    Barberia buscarPorId(ObjectId id);
    
    Barberia buscarPorIdBarbero(String idBarbero);

    List<Barberia> buscarTodas();

    List<Barberia> buscarActivas();

    Barberia buscarPorNombre(String nombre);
}
