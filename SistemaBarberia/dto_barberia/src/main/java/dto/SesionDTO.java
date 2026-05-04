package dto;

/**
 * DTO para transferir datos de inicio de sesión.
 * La contraseña se maneja como char[] para poder limpiarla de memoria
 * después de usarla, evitando que quede expuesta.
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class SesionDTO {

    private String usuario;
    private char[] contrasena;

    public SesionDTO() {
    }

    public SesionDTO(String usuario, char[] contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public char[] getContrasena() {
        return contrasena;
    }
    public void setContrasena(char[] contrasena) {
        this.contrasena = contrasena;
    }
}
