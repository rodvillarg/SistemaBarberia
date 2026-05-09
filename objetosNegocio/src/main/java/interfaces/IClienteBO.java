package interfaces;

import dto.ClienteDTO;
import dto.SesionDTO;
import exceptions.ClienteNoEncontradoException;
import exceptions.CredencialesInvalidasException;
import exceptions.ClienteNoEncontradoException;
import exceptions.UsuarioDuplicadoException;

/**
 * Interfaz de negocio para operaciones con Cliente.
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public interface IClienteBO {

    ClienteDTO registrar(ClienteDTO cliente) throws UsuarioDuplicadoException;

    ClienteDTO iniciarSesion(SesionDTO sesion)
            throws CredencialesInvalidasException, ClienteNoEncontradoException;

    ClienteDTO obtenerPorId(String id) throws ClienteNoEncontradoException;

    ClienteDTO obtenerPorUsuario(String usuario);

    void confirmarRolBarbero(String usuario);
    
    boolean esCliente(ClienteDTO cliente);
    
    boolean esBarbero(ClienteDTO cliente);
}
