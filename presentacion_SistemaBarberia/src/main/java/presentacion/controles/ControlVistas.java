/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.controles;

import javax.swing.JPanel;
import presentacion.PanelInicioSesion;
import presentacion.utilerias.VentanaPrincipal;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class ControlVistas {

    private final VentanaPrincipal ventana;

    public static final String pantallaLogin = "login";
    public static final String pantallaRegistro = "registro";
    public static final String pantallaBarberias = "barberias";
    public static final String pantallaServicios = "servicios";
    public static final String pantallaFechaHora = "fechaHora";
    public static final String pantallaConfirmacion = "confirmacion";
    public static final String pantallaMisCitas = "misCitas";
    public static final String pantallaMenuAdmin = "menuAdmin";
    public static final String pantallaGestionarCitas = "gestionarCitas";
    public static final String pantallaRegBarberia = "regBarberia";
    public static final String pantallaConfirmarCita = "confirmarCita";
    public static final String pantallaInfoBarberia = "infoBarberia";
    public static final String pantallaRegistroCuenta = "registroCuenta";
    public static final String pantallaPagoTarjeta = "pagoTarjeta";
    public static final String pantallaResenas = "resenas";
    public static final String pantallaAsignarHorario = "asignarHorario";
    public static final String pantallaGestionarServicios = "gestionarServicios";

    public ControlVistas() {
        this.ventana = new VentanaPrincipal();
    }

    public void registrarPanel(String nombre, JPanel panel) {
        ventana.agregarPanel(panel, nombre);
    }

    public void mostrar(String nombre) {
        ventana.mostrar(nombre);
        if (pantallaLogin.equals(nombre)) {
            PanelInicioSesion panel = getPanel(pantallaLogin);
            if (panel != null) {
                panel.limpiar();
            }
        }
    }

    public void iniciar() {
        ventana.setVisible(true);
    }

    public void cerrar() {
        ventana.dispose();
    }

    public <T extends JPanel> T getPanel(String nombre) {
        return ventana.obtenerPanel(nombre);
    }
}
