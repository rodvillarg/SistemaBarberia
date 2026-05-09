/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.negocios_iniciosesion;

import dto.ClienteDTO;
import dto.SesionDTO;
import exceptions.ClienteNoEncontradoException;
import exceptions.CredencialesInvalidasException;
import exceptions.UsuarioDuplicadoException;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public interface IInicioSesionFachada {

    ClienteDTO iniciarSesion(SesionDTO sesion)
            throws CredencialesInvalidasException, ClienteNoEncontradoException;

    ClienteDTO registrarCliente(ClienteDTO clienteDTO) throws UsuarioDuplicadoException;
    
    boolean esCliente(ClienteDTO cliente);
    
    boolean esBarbero(ClienteDTO cliente);
}
