package daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import conexion.ManejadorConexion;
import dominio.Resena;
import interfaces.IResenaDAO;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * DAO para la entidad Resena usando MongoDB.
 * @author Jesus Rodrigo Villegas - 261186
 */
public class ResenaDAO implements IResenaDAO {

    private final MongoCollection<Resena> coleccion;

    public ResenaDAO() {
        MongoDatabase db = ManejadorConexion.getInstancia().getBaseDatos();
        this.coleccion = db.getCollection("resenas", Resena.class);
    }

    @Override
    public Resena insertar(Resena res) {
        res.setId(new ObjectId());
        coleccion.insertOne(res);
        return res;
    }

    @Override
    public List<Resena> buscarPorBarberia(ObjectId idBarberia) {
        List<Resena> lista = new ArrayList<>();
        coleccion.find(eq("idBarberia", idBarberia)).into(lista);
        return lista;
    }

    @Override
    public List<Resena> buscarPorCliente(ObjectId idCliente) {
        List<Resena> lista = new ArrayList<>();
        coleccion.find(eq("idCliente", idCliente)).into(lista);
        return lista;
    }
}
