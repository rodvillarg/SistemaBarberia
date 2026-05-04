package daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import conexion.ManejadorConexion;
import dominio.Servicio;
import interfaces.IServicioDAO;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Implementación del DAO para la entidad Servicio usando MongoDB.
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class ServicioDAO implements IServicioDAO {

    /**
     *
     */
    private final MongoCollection<Servicio> coleccion;

    public ServicioDAO() {
        MongoDatabase db = ManejadorConexion.getInstancia().getBaseDatos();
        this.coleccion = db.getCollection("servicios", Servicio.class);
    }

    @Override
    public Servicio insertar(Servicio servicio) {
        servicio.setId(new ObjectId());
        coleccion.insertOne(servicio);
        return servicio;
    }

    @Override
    public List<Servicio> buscarPorBarberia(ObjectId idBarberia) {
        List<Servicio> lista = new ArrayList<>();
        coleccion.find(eq("idBarberia", idBarberia)).into(lista);
        return lista;
    }

    @Override
    public Servicio buscarPorId(ObjectId id) {
        return coleccion.find(eq("_id", id)).first();
    }
}
