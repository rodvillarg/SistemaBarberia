package mappers;

import dominio.Cliente;
import dto.ClienteDTO;
import org.bson.types.ObjectId;

/**
 * @author Jesus Rodrigo Villegas - 261186
 */
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId().toHexString());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setCorreo(cliente.getCorreo());
        dto.setTelefono(cliente.getTelefono());
        dto.setUsuario(cliente.getUsuario());
        dto.setRol(cliente.getRol());
        // contrasena nunca se mapea hacia el DTO
        return dto;
    }

    public Cliente toEntity(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        if (clienteDTO.getId() != null && !clienteDTO.getId().isBlank()) {
            try {
                cliente.setId(new ObjectId(clienteDTO.getId()));
            } catch (IllegalArgumentException e) {
            }
        }
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setCorreo(clienteDTO.getCorreo());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setUsuario(clienteDTO.getUsuario());
        cliente.setContrasena(clienteDTO.getContrasena());
        cliente.setRol(clienteDTO.getRol());
        return cliente;
    }
}
