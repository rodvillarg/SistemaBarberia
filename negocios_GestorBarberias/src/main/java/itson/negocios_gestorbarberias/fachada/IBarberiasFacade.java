/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.negocios_gestorbarberias.fachada;

import dto.BarberiaDTO;
import exceptions.BarberiaNoEncontradaException;
import java.util.List;
/**
 *
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public interface IBarberiasFacade {
    
    BarberiaDTO registrar(BarberiaDTO barberia);

    List<BarberiaDTO> obtenerBarberiasActivas();

    BarberiaDTO obtenerPorId(String id) throws BarberiaNoEncontradaException;

    BarberiaDTO obtenerPorNombre(String nombre) throws BarberiaNoEncontradaException;
}
