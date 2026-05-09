/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.negocios_gestorclientes.fachada;

import dto.ClienteDTO;
import dto.SesionDTO;
import exceptions.ClienteNoEncontradoException;
import exceptions.CredencialesInvalidasException;
import exceptions.UsuarioDuplicadoException;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public interface IClientesFacade {

    ClienteDTO registrar(ClienteDTO cliente) throws UsuarioDuplicadoException;

    ClienteDTO iniciarSesion(SesionDTO sesion) throws CredencialesInvalidasException, ClienteNoEncontradoException;

    ClienteDTO obtenerPorId(String id) throws ClienteNoEncontradoException;
    
    boolean esCliente(ClienteDTO cliente);
    
    boolean esBarbero(ClienteDTO cliente);
}
