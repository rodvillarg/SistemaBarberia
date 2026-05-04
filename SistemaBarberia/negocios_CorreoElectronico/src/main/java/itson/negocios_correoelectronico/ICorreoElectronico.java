package itson.negocios_correoelectronico;

/**
 * Interfaz para el servicio de envío de correos electrónicos.
 * @author Jesus Rodrigo Villegas - 261186
 *
 */
public interface ICorreoElectronico {

    /**
     * Envía un correo de confirmación de cita al cliente.
     *
     * @param destino  Dirección de correo del destinatario.
     * @param asunto   Asunto del mensaje.
     * @param cuerpoHtml Contenido HTML del correo.
     * @throws Exception Si ocurre un error durante el envío.
     */
    void enviarConfirmacionCita(String destino, String asunto, String cuerpoHtml) throws Exception;
}
