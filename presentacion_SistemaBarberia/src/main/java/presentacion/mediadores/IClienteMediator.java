/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package presentacion.mediadores;

import dto.ClienteDTO;
import exceptions.ClienteNoEncontradoException;
import exceptions.UsuarioDuplicadoException;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public interface IClienteMediator {

    ClienteDTO registrar(ClienteDTO cliente) throws UsuarioDuplicadoException;

    ClienteDTO obtenerPorId(String id) throws ClienteNoEncontradoException;
}
