package dto;

/**
 * DTO para Horario de barbería.
 * 
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class HorarioDTO {

    private String id;
    private String idBarberia;
    private String diaSemana;       // Ej: "LUNES", "MARTES"...
    private String horaApertura;    // Formato HH:mm
    private String horaCierre;      // Formato HH:mm

    public HorarioDTO() {}

    public HorarioDTO(String idBarberia, String diaSemana,
            String horaApertura, String horaCierre) {
        this.idBarberia   = idBarberia;
        this.diaSemana    = diaSemana;
        this.horaApertura = horaApertura;
        this.horaCierre   = horaCierre;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIdBarberia() {
        return idBarberia;
    }
    public void setIdBarberia(String idBarberia) {
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
