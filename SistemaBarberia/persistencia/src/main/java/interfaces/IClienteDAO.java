package interfaces;

import dominio.Cliente;
import org.bson.types.ObjectId;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public interface IClienteDAO {

    Cliente insertar(Cliente cliente);

    Cliente actualizar(Cliente cliente);

    Cliente buscarPorUsuario(String usuario);

    Cliente buscarPorId(ObjectId id);

    boolean existeUsuario(String usuario);
}
