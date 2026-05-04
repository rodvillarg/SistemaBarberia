package itson.negocios_correoelectronico;

import java.util.Properties;

/**
 * Configuración SMTP para el envío de correos electrónicos.
 * @author Jesus Rodrigo Villegas - 261186
 *
 * 
 */
public class ConfiguracionCorreoElectronico {

    private final String usuario;
    private final String contrasena;
    private final Properties props;

    public ConfiguracionCorreoElectronico() {
 
        this.usuario = "sistemabarberiac@gmail.com";
        this.contrasena = "mlcw yozi gfja esko";

        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
    }

    public String getUsuario() {
        return usuario;
    }
    public String getContrasena() {
        return contrasena;
    }
    public Properties getProps() {
        return props;
    }
}
