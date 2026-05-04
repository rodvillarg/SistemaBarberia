package interfaces;

import dto.ResenaDTO;
import java.util.List;

/**
 * @author Jesus Rodrigo Villegas - 261186
 */
public interface IResenaBO {

    ResenaDTO agregar(ResenaDTO resena);

    List<ResenaDTO> obtenerPorBarberia(String idBarberia);

    List<ResenaDTO> obtenerPorCliente(String idCliente);

    double calcularPromedio(String idBarberia);

}
