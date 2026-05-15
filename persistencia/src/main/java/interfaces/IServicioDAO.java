package interfaces;

import dominio.Servicio;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Interfaz que define las operaciones de acceso a datos para Servicio.
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public interface IServicioDAO {

    Servicio insertar(Servicio servicio);

    List<Servicio> buscarPorBarberia(ObjectId barberiaId);

    Servicio buscarPorId(ObjectId id);
    
    void eliminar(ObjectId id);
}
