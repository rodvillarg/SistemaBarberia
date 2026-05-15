package bos;

import daos.ResenaDAO;
import interfaces.IResenaDAO;
import dominio.Resena;
import dto.ResenaDTO;
import interfaces.IResenaBO;
import java.util.ArrayList;
import java.util.List;
import mappers.ResenaMapper;
import org.bson.types.ObjectId;

/**
 * @author Jesus Rodrigo Villegas - 261186
 */
public class ResenaBO implements IResenaBO {

    private final IResenaDAO resenaDAO;
    private final ResenaMapper mapper;

    public ResenaBO() {
        this.resenaDAO = new ResenaDAO();
        this.mapper = new ResenaMapper();
    }

    /**
     *
     * @param resenaDTO
     * @return
     */
    @Override
    public ResenaDTO agregar(ResenaDTO resenaDTO) {
        Resena entidad = mapper.toEntity(resenaDTO);
        Resena guardada = resenaDAO.insertar(entidad);

        return mapper.toDTO(guardada);
    }

    /**
     *
     * @param idBarberia
     * @return
     */
    @Override
    public List<ResenaDTO> obtenerPorBarberia(String idBarberia) {
        List<ResenaDTO> lista = new ArrayList<>();

        List<Resena> resenas = resenaDAO.buscarPorBarberia(new ObjectId(idBarberia));
        for (Resena r : resenas) {
            lista.add(mapper.toDTO(r));
        }

        return lista;
    }

    /**
     *
     * @param idCliente
     * @return
     */
    @Override
    public List<ResenaDTO> obtenerPorCliente(String idCliente) {
        List<ResenaDTO> lista = new ArrayList<>();

        List<Resena> resenas = resenaDAO.buscarPorCliente(new ObjectId(idCliente));
        for (Resena res : resenas) {
            lista.add(mapper.toDTO(res));
        }

        return lista;
    }

    /**
     *
     * @param idBarberia
     * @return
     */
    @Override
    public double calcularPromedio(String idBarberia) {
        List<Resena> resenas = resenaDAO.buscarPorBarberia(new ObjectId(idBarberia));

       if (resenas == null || resenas.isEmpty())
          return 0.0;

        double suma = 0;
        for (Resena r : resenas)
            suma += r.getCalificacion();

        return Math.round((suma / resenas.size()) * 10.0) / 10.0;
    }

}
