package interfaces;

import dominio.Resena;
import java.util.List;
import org.bson.types.ObjectId;

/** @author Jesus Rodrigo Villegas - 261186 */
public interface IResenaDAO {
    Resena insertar(Resena resena);
    List<Resena> buscarPorBarberia(ObjectId idBarberia);
    List<Resena> buscarPorCliente(ObjectId idCliente);
}
