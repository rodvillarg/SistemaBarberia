package daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import conexion.ManejadorConexion;
import dominio.Pago;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * DAO para la entidad Pago usando MongoDB.
 * @author Jesus Rodrigo Villegas - 261186
 */
public class PagoDAO {

    private final MongoCollection<Pago> coleccion;

    public PagoDAO() {
        MongoDatabase db = ManejadorConexion.getInstancia().getBaseDatos();
        this.coleccion = db.getCollection("pagos", Pago.class);
    }

    public Pago insertar(Pago pago) {
        pago.setId(new ObjectId());
        coleccion.insertOne(pago);
        return pago;
    }

    public Pago buscarPorCita(ObjectId idCita) {
        return coleccion.find(eq("idCita", idCita)).first();
    }

    public List<Pago> buscarTodos() {
        List<Pago> lista = new ArrayList<>();
        coleccion.find().into(lista);
        return lista;
    }
}
