package bos;

import daos.ServicioDAO;
import dominio.Servicio;
import dto.ServicioDTO;
import exceptions.ServicioNoEncontradoException;
import interfaces.IServicioBO;
import java.util.ArrayList;
import java.util.List;
import mappers.ServicioMapper;
import org.bson.types.ObjectId;

public class ServicioBO implements IServicioBO {

    private final ServicioDAO servicioDAO;
    private final ServicioMapper mapper;


    private static ServicioBO instance;

    public static synchronized ServicioBO getInstancia() {
        if (instance == null) {
            instance = new ServicioBO();
        }
        return instance;
    }
    private ServicioBO() {
        this.servicioDAO = new ServicioDAO();
        this.mapper = new ServicioMapper();
    }

    /**
     *
     * @param servicioDTO
     * @return
     * @author Jesus Rodrigo Villegas - 261186
 */
    @Override
    public ServicioDTO registrar(ServicioDTO servicioDTO) {
        Servicio entidad = mapper.toEntity(servicioDTO);
        Servicio guardado = servicioDAO.insertar(entidad);
        return mapper.toDTO(guardado);
    }

    /**
     *
     * @param barberiaId
     * @return
     */
    @Override
    public List<ServicioDTO> obtenerServiciosPorBarberia(String barberiaId) {
        List<Servicio> entidades = servicioDAO.buscarPorBarberia(new ObjectId(barberiaId));
        List<ServicioDTO> dtos = new ArrayList<>();
        for (Servicio serv : entidades) {
            dtos.add(mapper.toDTO(serv));
        }
        return dtos;
    }

    @Override
    public ServicioDTO obtenerPorId(String id) throws ServicioNoEncontradoException {
        Servicio servicio = servicioDAO.buscarPorId(new ObjectId(id));
        if (servicio == null) {
            throw new ServicioNoEncontradoException(
                    "No se encontró el servicio con id: " + id);
        }
        return mapper.toDTO(servicio);
    }
}
