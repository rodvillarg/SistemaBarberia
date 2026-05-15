/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.utilerias;

import dto.ClienteDTO;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class GestorSesion {
    
    private static ClienteDTO clienteActivo;

    /**
     * Retorna el cliente con sesión activa.
     *
     * @return ClienteDTO del cliente logueado, o null si no hay sesión.
     */
    public static ClienteDTO getClienteActivo() {
        return clienteActivo;
    }

    /**
     * Establece el cliente con sesión activa.
     *
     * @param cliente ClienteDTO que inició sesión.
     */
    public static void setClienteActivo(ClienteDTO cliente) {
        clienteActivo = cliente;
    }

    /**
     * Elimina los datos de la sesión actual (cierre de sesión).
     */
    public static void cerrarSesion() {
        clienteActivo = null;
    }

    /**
     * Verifica si hay una sesión activa.
     *
     * @return true si hay cliente logueado.
     */
    public static boolean haySesion() {
        return clienteActivo != null;
    }
    
}


