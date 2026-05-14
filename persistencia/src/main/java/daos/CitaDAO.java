package daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;
import conexion.ManejadorConexion;
import dominio.Cita;
import interfaces.ICitaDAO;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * DAO para la entidad Cita usando MongoDB.
 * fechaHora se almacena como String "YYYY/MM/DD H:mm".
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class CitaDAO implements ICitaDAO {

    private final MongoCollection<Cita> coleccion;

    public CitaDAO() {
        MongoDatabase db = ManejadorConexion.getInstancia().getBaseDatos();
        this.coleccion = db.getCollection("citas", Cita.class);
    }

    @Override
    public Cita insertar(Cita cita) {
        cita.setId(new ObjectId());
        coleccion.insertOne(cita);

        return cita;
    }

    @Override
    public Cita buscarPorId(ObjectId id) {
        return coleccion.find(eq("_id", id)).first();
    }

    @Override
    public List<Cita> buscarPorCliente(ObjectId idCliente) {
        List<Cita> lista = new ArrayList<>();
        coleccion.find(eq("idCliente", idCliente)).into(lista);
        return lista;
    }

    @Override
    public List<Cita> buscarPorBarberia(ObjectId idBarberia) {
        List<Cita> lista = new ArrayList<>();
        coleccion.find(eq("idBarberia", idBarberia)).into(lista);
        return lista;
    }

    /**
     * Retorna todos los fechaHora ocupados en una barberia para una fecha dada.
     * La fecha viene como "YYYY/MM/DD" y busca todas las citas cuyo fechaHora
     * empiece con esa cadena.
     */
    @Override
    public List<String> buscarHorasOcupadas(ObjectId idBarberia, String fecha) {
        List<String> ocupadas = new ArrayList<>();
        List<Cita> citas = new ArrayList<>();
        coleccion.find(
                and(eq("idBarberia", idBarberia),
                    regex("fechaHora", "^" + fecha),
                    com.mongodb.client.model.Filters.ne("estado",
                            dto.enums.EstadoCita.CANCELADA))
        ).into(citas);
        for (Cita c : citas) {
            if (c.getFechaHora() != null) {
                // Extraer solo la parte de hora "H:mm"
                String[] partes = c.getFechaHora().split(" ");
                if (partes.length > 1) ocupadas.add(partes[1]);
            }
        }
        return ocupadas;
    }

    @Override
    public boolean existeConflicto(ObjectId idBarberia, String fechaHora) {
        return coleccion.find(
                and(eq("idBarberia", idBarberia),
                    eq("fechaHora", fechaHora),
                    com.mongodb.client.model.Filters.ne("estado",
                            dto.enums.EstadoCita.CANCELADA))
        ).first() != null;

    }

    @Override
    public void cancelarCita(ObjectId idCita) {
        coleccion.updateOne(
                com.mongodb.client.model.Filters.eq("_id", idCita),
                com.mongodb.client.model.Updates.set("estado",
                        dto.enums.EstadoCita.CANCELADA));
    }
    
    @Override
    public void completarCita(ObjectId idCita) {
        coleccion.updateOne(
                com.mongodb.client.model.Filters.eq("_id", idCita),
                com.mongodb.client.model.Updates.set("estado",
                        dto.enums.EstadoCita.COMPLETADA));
    }

    @Override
    public boolean existeConflictoCliente(ObjectId idCliente, String fechaHora) {
        return coleccion.find(
                and(eq("idCliente", idCliente),
                    eq("fechaHora", fechaHora),
                    com.mongodb.client.model.Filters.ne("estado",
                            dto.enums.EstadoCita.CANCELADA))
        ).first() != null;
    }
}
