/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.negocios_gestorclientes.fachada;

import dto.ClienteDTO;
import dto.SesionDTO;
import exceptions.ClienteNoEncontradoException;
import exceptions.CredencialesInvalidasException;
import exceptions.UsuarioDuplicadoException;
import itson.negocios_gestorclientes.GestorClientes;
import itson.negocios_gestorclientes.IGestorClientes;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class ClientesFacade implements IClientesFacade {

    private final IGestorClientes gestorClientes;

    public ClientesFacade() {
        this.gestorClientes = new GestorClientes();
    }

    @Override
    public ClienteDTO registrar(ClienteDTO cliente) throws UsuarioDuplicadoException {
        return gestorClientes.registrar(cliente);
    }

    @Override
    public ClienteDTO iniciarSesion(SesionDTO sesion)
            throws CredencialesInvalidasException, ClienteNoEncontradoException {
        return gestorClientes.iniciarSesion(sesion);
    }

    @Override
    public ClienteDTO obtenerPorId(String id) throws ClienteNoEncontradoException {
        return gestorClientes.obtenerPorId(id);
    }
    
    @Override
    public boolean esCliente(ClienteDTO cliente) {
        return gestorClientes.esCliente(cliente);
    }

    @Override
    public boolean esBarbero(ClienteDTO cliente) {
        return gestorClientes.esBarbero(cliente);
    }
}

