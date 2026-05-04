package interfaces;

import dominio.Horario;
import java.util.List;
import org.bson.types.ObjectId;

/** @author Jesus Rodrigo Villegas - 261186 */
public interface IHorarioDAO {
    Horario insertar(Horario horario);
    List<Horario> buscarPorBarberia(ObjectId idBarberia);
    Horario buscarPorId(ObjectId id);
}
