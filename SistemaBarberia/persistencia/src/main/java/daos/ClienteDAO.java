package daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import conexion.ManejadorConexion;
import dominio.Cliente;
import interfaces.IClienteDAO;
import org.bson.types.ObjectId;

/**
 * Implementación del DAO para la entidad Cliente usando MongoDB.
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class ClienteDAO implements IClienteDAO {

    private final MongoCollection<Cliente> coleccion;

    public ClienteDAO() {
        MongoDatabase db = ManejadorConexion.getInstancia().getBaseDatos();
        this.coleccion = db.getCollection("clientes", Cliente.class);
    }

    @Override
    public Cliente insertar(Cliente cliente) {
        cliente.setId(new ObjectId());
        coleccion.insertOne(cliente);
        return cliente;
    }

    @Override
    public Cliente buscarPorUsuario(String user) {
        return coleccion.find(eq("usuario", user)).first();
    }

    @Override
    public Cliente buscarPorId(ObjectId id) {
        return coleccion.find(eq("_id", id)).first();
    }

    @Override
    public Cliente actualizar(Cliente cliente) {
        coleccion.replaceOne(
                com.mongodb.client.model.Filters.eq("_id", cliente.getId()),
                cliente);

        return cliente;
    }

    @Override
    public boolean existeUsuario(String usuario) {
        return coleccion.find(eq("usuario", usuario)).first() != null;
    }
}
