/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.mediadores;

import dto.ClienteDTO;
import exceptions.ClienteNoEncontradoException;
import exceptions.UsuarioDuplicadoException;
import itson.negocios_gestorclientes.fachada.ClientesFacade;
import itson.negocios_gestorclientes.fachada.IClientesFacade;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class ClienteMediator implements IClienteMediator {

    private final IClientesFacade facadeClientes;

    public ClienteMediator() {
        this.facadeClientes = new ClientesFacade();
    }

    @Override
    public ClienteDTO registrar(ClienteDTO cliente) throws UsuarioDuplicadoException {
        return facadeClientes.registrar(cliente);
    }

    @Override
    public ClienteDTO obtenerPorId(String id) throws ClienteNoEncontradoException {
        return facadeClientes.obtenerPorId(id);
    }
}
