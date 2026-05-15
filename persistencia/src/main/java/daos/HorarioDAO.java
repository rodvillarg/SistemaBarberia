package daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import conexion.ManejadorConexion;
import dominio.Horario;
import interfaces.IHorarioDAO;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * DAO para la entidad Horario usando MongoDB.
 * @author Jesus Rodrigo Villegas - 261186
 */
public class HorarioDAO implements IHorarioDAO {

    private final MongoCollection<Horario> coleccion;

    public HorarioDAO() {
        MongoDatabase db = ManejadorConexion.getInstancia().getBaseDatos();
        this.coleccion = db.getCollection("horarios", Horario.class);
    }

    @Override
    public Horario insertar(Horario h) {
        h.setId(new ObjectId());
        coleccion.insertOne(h);

        return h;
    }

    @Override
    public List<Horario> buscarPorBarberia(ObjectId idBarberia) {
        List<Horario> lista = new ArrayList<>();
        coleccion.find(eq("idBarberia", idBarberia)).into(lista);
        return lista;
    }

    @Override
    public Horario buscarPorId(ObjectId id) {
        return coleccion.find(eq("_id", id)).first();
    }
    
    @Override
    public void eliminarPorBarberia(ObjectId idBarberia) {
        coleccion.deleteMany(eq("idBarberia", idBarberia));
    }
}
