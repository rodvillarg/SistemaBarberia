package interfaces;

import dominio.Cita;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Interfaz DAO para Cita.
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public interface ICitaDAO {

    Cita insertar(Cita cita);

    Cita buscarPorId(ObjectId id);

    List<Cita> buscarPorCliente(ObjectId idCliente);

    List<Cita> buscarPorBarberia(ObjectId idBarberia);

    /** Retorna todos los fechaHora (String) ocupados en una barbería en una fecha dada. */
    List<String> buscarHorasOcupadas(ObjectId idBarberia, String fecha);

    /** Conflicto: misma barbería + mismo fechaHora exacto. */
    boolean existeConflicto(ObjectId idBarberia, String fechaHora);

    boolean existeConflictoCliente(ObjectId idCliente, String fechaHora);

    void cancelarCita(ObjectId idCita);
}
