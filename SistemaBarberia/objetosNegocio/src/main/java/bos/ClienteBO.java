package bos;

import daos.ClienteDAO;
import dominio.Cliente;
import dto.ClienteDTO;
import dto.SesionDTO;
import dto.enums.RolUsuario;
import exceptions.ClienteNoEncontradoException;
import exceptions.CredencialesInvalidasException;
import exceptions.UsuarioDuplicadoException;
import interfaces.IClienteBO;
import mappers.ClienteMapper;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;

public class ClienteBO implements IClienteBO {

    private static ClienteBO instance;

    private final ClienteDAO clienteDAO;
    private final ClienteMapper mapper;

    private ClienteBO() {
        this.clienteDAO = new ClienteDAO();
        this.mapper = new ClienteMapper();
    }

    public static synchronized ClienteBO getInstance() {
        if (instance == null) {
            instance = new ClienteBO();
        }
        return instance;
    }

    /**
     * Registra un nuevo cliente en el sistema.
     * Verifica que el usuario no este duplicado y hashea
     * la contrasena con BCrypt antes de persistir.
     *
     * @param clienteDTO datos del cliente a registrar
     * @return ClienteDTO con el ID asignado por MongoDB
     * @throws UsuarioDuplicadoException si el nombre de usuario ya existe
     * @author Jesus Rodrigo Villegas - 261186
 */
    @Override
    public ClienteDTO registrar(ClienteDTO clienteDTO) throws UsuarioDuplicadoException {
        if (clienteDAO.existeUsuario(clienteDTO.getUsuario())) {
            throw new UsuarioDuplicadoException(
                    "El usuario '" + clienteDTO.getUsuario() + "' ya esta registrado.");
        }
        if (clienteDTO.getRol() == null) {
            clienteDTO.setRol(RolUsuario.CLIENTE);
        }
        Cliente entidad = mapper.toEntity(clienteDTO);
        // Hashear la contrasena con BCrypt antes de guardar
        String hash = BCrypt.hashpw(clienteDTO.getContrasena(), BCrypt.gensalt());
        entidad.setContrasena(hash);
        Cliente guardado = clienteDAO.insertar(entidad);
        return mapper.toDTO(guardado);
    }

    @Override
    public ClienteDTO iniciarSesion(SesionDTO sesion)
            throws CredencialesInvalidasException, ClienteNoEncontradoException {
        Cliente cliente = clienteDAO.buscarPorUsuario(sesion.getUsuario());
        if (cliente == null)
            throw new ClienteNoEncontradoException("El usuario no existe.");
        // Comparar con BCrypt, no texto plano
        String contrasenaPlano = new String(sesion.getContrasena());
        boolean contrasenaValida = BCrypt.checkpw(contrasenaPlano, cliente.getContrasena());
        if (!contrasenaValida)
            throw new CredencialesInvalidasException("Usuario o contrasena incorrectos.");
        return mapper.toDTO(cliente);
    }

    @Override
    public ClienteDTO obtenerPorId(String id) throws ClienteNoEncontradoException {
        if (id == null || id.isBlank()) {
            throw new ClienteNoEncontradoException("El ID del cliente no es valido.");
        }
        ObjectId oid;
        try {
            oid = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            throw new ClienteNoEncontradoException("El ID del cliente no tiene un formato valido.");
        }
        Cliente cliente = clienteDAO.buscarPorId(oid);
        if (cliente == null) {
            throw new ClienteNoEncontradoException(
                    "No se encontro el cliente con id: " + id);
        }
        return mapper.toDTO(cliente);
    }

    @Override
    public ClienteDTO obtenerPorUsuario(String usuario) {
        Cliente cliente = clienteDAO.buscarPorUsuario(usuario);
        if (cliente != null) {
            return mapper.toDTO(cliente);
        }
        return null;
    }

    public boolean esCliente(ClienteDTO cliente) {
        return cliente.getRol() == dto.enums.RolUsuario.CLIENTE;
    }

    public boolean esBarbero(ClienteDTO cliente) {
        return cliente.getRol() == dto.enums.RolUsuario.BARBERO;
    }

    /**
     *
     * @param usuario
     */
    public void confirmarRolBarbero(String usuario) {
        // TODO: mover esto a un metodo del DAO mas adelante
        Cliente cliente = clienteDAO.buscarPorUsuario(usuario);
        if (cliente != null && !dto.enums.RolUsuario.BARBERO.equals(cliente.getRol())) {
            cliente.setRol(dto.enums.RolUsuario.BARBERO);
            clienteDAO.actualizar(cliente);
            System.out.println("[ClienteBO] Rol de '" + usuario + "' actualizado a BARBERO");
        }
    }
}
