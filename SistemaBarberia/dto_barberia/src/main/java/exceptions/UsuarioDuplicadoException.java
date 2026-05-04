package exceptions;
/**
 * @author Jesus Rodrigo Villegas - 261186
 */
public class UsuarioDuplicadoException extends NegocioException {
    public UsuarioDuplicadoException(String mensaje) { super(mensaje); }
}
