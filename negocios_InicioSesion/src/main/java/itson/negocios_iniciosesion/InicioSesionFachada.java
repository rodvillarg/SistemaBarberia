package itson.negocios_iniciosesion;

import dto.ClienteDTO;
import dto.SesionDTO;
import exceptions.ClienteNoEncontradoException;
import exceptions.CredencialesInvalidasException;
import exceptions.UsuarioDuplicadoException;
import itson.negocios_gestorclientes.fachada.ClientesFacade;
import itson.negocios_gestorclientes.fachada.IClientesFacade;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class InicioSesionFachada implements IInicioSesionFachada {

    private final IClientesFacade facadeClientes;

    public InicioSesionFachada() {
        this.facadeClientes = new ClientesFacade();
    }

    @Override
    public ClienteDTO iniciarSesion(SesionDTO sesion)
            throws CredencialesInvalidasException, ClienteNoEncontradoException {
        return facadeClientes.iniciarSesion(sesion);
    }

    @Override
    public ClienteDTO registrarCliente(ClienteDTO clienteDTO) throws UsuarioDuplicadoException {
        return facadeClientes.registrar(clienteDTO);
    }
    
    @Override
    public boolean esCliente(ClienteDTO cliente) {
        return facadeClientes.esCliente(cliente);
    }

    @Override
    public boolean esBarbero(ClienteDTO cliente) {
        return facadeClientes.esBarbero(cliente);
    }
}