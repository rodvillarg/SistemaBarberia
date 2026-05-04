package itson.negocios_correoelectronico;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Date;

/**
 * Implementación del servicio de envío de correos electrónicos usando SMTP.
 * @author Jesus Rodrigo Villegas - 261186
 *
 */
public class CorreoElectronico implements ICorreoElectronico {

    private final ConfiguracionCorreoElectronico config;
    private final Session session;

    public CorreoElectronico(ConfiguracionCorreoElectronico config) {
        this.config = config;
        this.session = Session.getInstance(config.getProps(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getUsuario(), config.getContrasena());
            }
        });
    }

    /**
     * Envía un correo HTML de confirmación de cita (sin adjunto).
     *
     * @param destino    Correo del destinatario.
     * @param asunto     Asunto del mensaje.
     * @param cuerpoHtml Contenido HTML del cuerpo.
     * @throws Exception Si ocurre un error SMTP.
     */
    @Override
    public void enviarConfirmacionCita(String destino, String asunto, String cuerpoHtml) throws Exception {
        Message mensaje = new MimeMessage(session);
        mensaje.setFrom(new InternetAddress(config.getUsuario()));
        mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
        mensaje.setSubject(asunto);
        mensaje.setSentDate(new Date());
        mensaje.setContent(cuerpoHtml, "text/html; charset=utf-8");
        Transport.send(mensaje);
    }
}
