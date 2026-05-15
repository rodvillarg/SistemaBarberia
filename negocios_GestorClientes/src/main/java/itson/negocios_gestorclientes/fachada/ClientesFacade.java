/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.negocios_gestorclientes.fachada;

import bos.ClienteBO;
import dto.ClienteDTO;
import dto.SesionDTO;
import exceptions.ClienteNoEncontradoException;
import exceptions.CredencialesInvalidasException;
import exceptions.UsuarioDuplicadoException;
import interfaces.IClienteBO;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class ClientesFacade implements IClientesFacade {

    private final IClienteBO clienteBO;

    public ClientesFacade() {
        this.clienteBO = new ClienteBO();
    }

    @Override
    public ClienteDTO registrar(ClienteDTO cliente) throws UsuarioDuplicadoException {
        return clienteBO.registrar(cliente);
    }

    @Override
    public ClienteDTO iniciarSesion(SesionDTO sesion)
            throws CredencialesInvalidasException, ClienteNoEncontradoException {
        return clienteBO.iniciarSesion(sesion);
    }

    @Override
    public ClienteDTO obtenerPorId(String id) throws ClienteNoEncontradoException {
        return clienteBO.obtenerPorId(id);
    }

    @Override
    public boolean esCliente(ClienteDTO cliente) {
        return clienteBO.esCliente(cliente);
    }

    @Override
    public boolean esBarbero(ClienteDTO cliente) {
        return clienteBO.esBarbero(cliente);
    }
}

