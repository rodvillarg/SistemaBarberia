package bos;

import daos.BarberiaDAO;
import dominio.Barberia;
import dto.BarberiaDTO;
import exceptions.BarberiaNoEncontradaException;
import interfaces.IBarberiaBO;
import java.util.ArrayList;
import java.util.List;
import mappers.BarberiaMapper;
import org.bson.types.ObjectId;

    /**
     *
     * 
     * @author Jesus Rodrigo Villegas - 261186
    */

public class BarberiaBO implements IBarberiaBO {

    private final BarberiaDAO barberiaDAO;
    private final BarberiaMapper mapper;


    private static BarberiaBO instancia;

    public static synchronized BarberiaBO getInstancia() {
        if (instancia == null) {
            instancia = new BarberiaBO();
        }
        return instancia;
    }
    private BarberiaBO() {
        this.barberiaDAO = new BarberiaDAO();
        this.mapper = new BarberiaMapper();
    }

    /**
     *
     * @param barberiaDTO
     * @return
     * 
 */
    @Override
    public BarberiaDTO registrar(BarberiaDTO barberiaDTO) {
        Barberia entidad = mapper.toEntity(barberiaDTO);
        // Las barberias registradas desde el sistema arrancan como activas
        entidad.setActiva(true);
        Barberia guardada = barberiaDAO.insertar(entidad);
        return mapper.toDTO(guardada);
    }

    @Override
    public void actualizarIdBarbero(String idBarberia, String idBarbero) {
        barberiaDAO.actualizarIdBarbero(
                new org.bson.types.ObjectId(idBarberia), idBarbero);
    }


    /**
     * Retorna unicamente las barberias con estado activo = true.
     *
     * @return lista de barberias activas, o lista vacia si no hay ninguna.
     */
    /**
     *
     * @return
     */
    @Override
    public List<BarberiaDTO> obtenerBarberiasActivas() {
        List<Barberia> entidades = barberiaDAO.buscarActivas();
        List<BarberiaDTO> dtos = new ArrayList<>();
        for (Barberia b : entidades) {
            dtos.add(mapper.toDTO(b));
        }
        return dtos;
    }

    @Override
    public BarberiaDTO obtenerPorNombre(String nombre) throws BarberiaNoEncontradaException {
        Barberia entidad = barberiaDAO.buscarPorNombre(nombre);
        if (entidad == null) {
            throw new BarberiaNoEncontradaException(
                    "No se encontró la barbería: " + nombre);
        }
        return mapper.toDTO(entidad);
    }

    /**
     *
     * @param id
     * @return
     * @throws exceptions.BarberiaNoEncontradaException
     */
    @Override
    public BarberiaDTO obtenerPorId(String id) throws BarberiaNoEncontradaException {
        Barberia barberia = barberiaDAO.buscarPorId(new ObjectId(id));
        if (barberia == null) {
            throw new BarberiaNoEncontradaException(
                    "No se encontró la barbería con id: " + id);
        }
        return mapper.toDTO(barberia);
    }

    @Override
    public BarberiaDTO obtenerPorBarbero(String idBarbero) {
        Barberia barberia = barberiaDAO.buscarPorIdBarbero(idBarbero);
        if (barberia == null) {
            return null;
        }
        return mapper.toDTO(barberia);
    }
}