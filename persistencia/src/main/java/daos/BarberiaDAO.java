package daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import conexion.ManejadorConexion;
import dominio.Barberia;
import interfaces.IBarberiaDAO;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * 
 * @author Jesus Rodrigo Villegas - 261186
 */
public class BarberiaDAO implements IBarberiaDAO {

    private final MongoCollection<Barberia> coleccion;

    public BarberiaDAO() {
        MongoDatabase db = ManejadorConexion.getInstancia().getBaseDatos();
        this.coleccion = db.getCollection("barberias", Barberia.class);
    }

    @Override
    public Barberia insertar(Barberia barberia) {
        barberia.setId(new ObjectId());
        coleccion.insertOne(barberia);

        return barberia;
    }


    @Override
    public void actualizarIdBarbero(ObjectId idBarberia, String idBarbero) {
        if (idBarberia == null || idBarbero == null || idBarbero.isBlank()) {
            return;
        }

        coleccion.updateOne(
                eq("_id", idBarberia),
                com.mongodb.client.model.Updates.set("idBarbero", new ObjectId(idBarbero))
        );
    }

    @Override
    public Barberia buscarPorId(ObjectId id) {
        return coleccion.find(eq("_id", id)).first();
    }


    @Override
    public List<Barberia> buscarTodas() {
        List<Barberia> lista = new ArrayList<>();
        coleccion.find().into(lista);
        return lista;
    }

    @Override
    public Barberia buscarPorNombre(String nombre) {
        return coleccion.find(
                com.mongodb.client.model.Filters.eq("nombre", nombre)).first();
    }

    public List<Barberia> buscarActivas() {
        List<Barberia> lista = new ArrayList<>();
        coleccion.find(eq("activa", true)).into(lista);
        return lista;
    }
    
    @Override
    public Barberia buscarPorIdBarbero(String idBarbero) {
        if (idBarbero == null || idBarbero.isBlank()) {
            return null;
        }

        try {
            return coleccion.find(
                    eq("idBarbero", new ObjectId(idBarbero))
            ).first();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}