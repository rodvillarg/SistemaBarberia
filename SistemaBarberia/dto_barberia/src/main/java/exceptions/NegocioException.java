package exceptions;

/**
 * Excepción base para errores de la capa de negocio.
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class NegocioException extends Exception {

    public NegocioException(String mensaje) {
        super(mensaje);
    }

    public NegocioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
