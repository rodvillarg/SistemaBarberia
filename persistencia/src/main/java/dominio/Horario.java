package dominio;

import java.time.LocalTime;
import org.bson.types.ObjectId;

/**
 * Entidad de dominio Horario para MongoDB.
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class Horario {

    private ObjectId id;
    private ObjectId idBarberia;
    private String diaSemana;
    private String horaApertura;
    private String horaCierre;

    public Horario() {}

    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    public ObjectId getIdBarberia() {
        return idBarberia;
    }
    public void setIdBarberia(ObjectId idBarberia) {
        this.idBarberia = idBarberia;
    }
    public String getDiaSemana() {
        return diaSemana;
    }
    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }
    public String getHoraApertura() {
        return horaApertura;
    }
    public void setHoraApertura(String horaApertura) {
        this.horaApertura = horaApertura;
    }
    public String getHoraCierre() {
        return horaCierre;
    }
    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }

}
