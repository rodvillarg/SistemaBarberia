/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.controles;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import presentacion.utilerias.GestorSesion;
import conexion.ManejadorConexion;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class ControlVistas {

    private static JFrame framePrincipal;
    private static JPanel contenedor;
    private static CardLayout cardLayout;

    private static presentacion.PanelInicioSesion       panelLogin;
    private static presentacion.PanelRegistro           panelRegistro;
    private static presentacion.PanelSeleccionBarberia  panelBarberias;
    private static presentacion.PanelSeleccionServicio  panelServicios;
    private static presentacion.PanelSeleccionFechaHora panelFechaHora;
    private static presentacion.PanelConfirmacion       panelConfirmacion;
    private static presentacion.PanelMisCitas           panelMisCitas;
    private static presentacion.PanelResenas            panelResenas;
    private static presentacion.PanelGestionarMisCitas  panelGestionarMisCitas;
    private static presentacion.PanelMenuAdmin          panelMenuAdmin;
    private static presentacion.PanelGestionCitas       panelGestionCitas;
    private static presentacion.PanelRegistroBarberia   panelRegistroBarberia;
    private static presentacion.PanelConfirmarCita      panelConfirmarCita;
    private static presentacion.PanelInfoBarberia       panelInfoBarberia;
    private static presentacion.PanelRegistroCuenta     panelRegistroCuenta;
    private static presentacion.PanelPagoTarjeta        panelPagoTarjeta;
    private static presentacion.PanelAsignarHorario panelAsignarHorario;

    public static final String pantallaLogin             = "login";
    public static final String pantallaRegistro          = "registro";
    public static final String pantallaBarberias         = "barberias";
    public static final String pantallaServicios         = "servicios";
    public static final String pantallaFechaHora         = "fechaHora";
    public static final String pantallaConfirmacion      = "confirmacion";
    public static final String pantallaMisCitas          = "misCitas";
    public static final String pantallaMenuAdmin         = "menuAdmin";
    public static final String pantallaGestionCitas      = "gestionCitas";
    public static final String pantallaRegBarberia       = "regBarberia";
    public static final String pantallaAdminGestionCitas = "adminGestionCitas";
    public static final String pantallaConfirmarCita     = "confirmarCita";
    public static final String pantallaInfoBarberia      = "infoBarberia";
    public static final String pantallaRegistroCuenta    = "registroCuenta";
    public static final String pantallaPagoTarjeta       = "pagoTarjeta";
    public static final String pantallaResenas           = "resenas";
    public static final String pantallaGestionarMisCitas = "gestionarMisCitas";
    public static final String pantallaAsignarHorario = "asignarHorario";

    static {
        framePrincipal = new JFrame("Sistema de Barberias");
        framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePrincipal.setSize(1000, 680);
        framePrincipal.setLocationRelativeTo(null);
        framePrincipal.setResizable(false);
        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);
        framePrincipal.add(contenedor, BorderLayout.CENTER);
    }

    private ControlVistas() {}

    public static void registrarPanel(presentacion.PanelInicioSesion panel) {
        panelLogin = panel;
        contenedor.add(panel, pantallaLogin);
    }
    public static void registrarPanel(presentacion.PanelRegistro panel) {
        panelRegistro = panel;
        contenedor.add(panel, pantallaRegistro);
    }
    public static void registrarPanel(presentacion.PanelSeleccionBarberia panel) {
        panelBarberias = panel;
        contenedor.add(panel, pantallaBarberias);
    }
    public static void registrarPanel(presentacion.PanelSeleccionServicio panel) {
        panelServicios = panel;
        contenedor.add(panel, pantallaServicios);
    }
    public static void registrarPanel(presentacion.PanelSeleccionFechaHora panel) {
        panelFechaHora = panel;
        contenedor.add(panel, pantallaFechaHora);
    }
    public static void registrarPanel(presentacion.PanelConfirmacion panel) {
        panelConfirmacion = panel;
        contenedor.add(panel, pantallaConfirmacion);
    }
    public static void registrarPanel(presentacion.PanelMisCitas panel) {
        panelMisCitas = panel;
        contenedor.add(panel, pantallaMisCitas);
    }
    public static void registrarPanel(presentacion.PanelMenuAdmin panel) {
        panelMenuAdmin = panel;
        contenedor.add(panel, pantallaMenuAdmin);
    }
    public static void registrarPanel(presentacion.PanelGestionCitas panel) {
        panelGestionCitas = panel;
        contenedor.add(panel, pantallaGestionCitas);
    }
    public static void registrarPanel(presentacion.PanelAdminGestionCitas panel) {
        contenedor.add(panel, pantallaAdminGestionCitas);
    }
    public static void registrarPanel(presentacion.PanelRegistroBarberia panel) {
        panelRegistroBarberia = panel;
        contenedor.add(panel, pantallaRegBarberia);
    }
    public static void registrarPanel(presentacion.PanelConfirmarCita panel) {
        panelConfirmarCita = panel;
        contenedor.add(panel, pantallaConfirmarCita);
    }
    public static void registrarPanel(presentacion.PanelInfoBarberia panel) {
        panelInfoBarberia = panel;
        contenedor.add(panel, pantallaInfoBarberia);
    }
    public static void registrarPanel(presentacion.PanelRegistroCuenta panel) {
        panelRegistroCuenta = panel;
        contenedor.add(panel, pantallaRegistroCuenta);
    }
    public static void registrarPanel(presentacion.PanelPagoTarjeta panel) {
        panelPagoTarjeta = panel;
        contenedor.add(panel, pantallaPagoTarjeta);
    }
    public static void registrarPanel(presentacion.PanelResenas panel) {
        panelResenas = panel;
        contenedor.add(panel, pantallaResenas);
    }
    public static void registrarPanel(presentacion.PanelGestionarMisCitas panel) {
        panelGestionarMisCitas = panel;
        contenedor.add(panel, pantallaGestionarMisCitas);
    }
    
    public static void registrarPanel(presentacion.PanelAsignarHorario panel) {
        panelAsignarHorario = panel;
        contenedor.add(panel, pantallaAsignarHorario);
    }

    public static void mostrar(String nombre) {
        cardLayout.show(contenedor, nombre);
        if (pantallaLogin.equals(nombre) && panelLogin != null) {
            panelLogin.limpiar();
        }
    }

    public static void iniciar() {
        framePrincipal.setVisible(true);
    }
    
    public static void cerrarVista() {
        framePrincipal.dispose();
    }
    
    public static presentacion.PanelSeleccionServicio getPanelServicios() {
        return panelServicios;
    }

    public static presentacion.PanelSeleccionFechaHora getPanelFechaHora() {
        return panelFechaHora;
    }

    public static presentacion.PanelConfirmacion getPanelConfirmacion() {
        return panelConfirmacion;
    }

    public static presentacion.PanelMisCitas getPanelMisCitas() {
        return panelMisCitas;
    }

    public static presentacion.PanelSeleccionBarberia getPanelBarberias() {
        return panelBarberias;
    }

    public static presentacion.PanelMenuAdmin getPanelMenuAdmin() {
        return panelMenuAdmin;
    }

    public static presentacion.PanelGestionCitas getPanelGestionCitas() {
        return panelGestionCitas;
    }

    public static presentacion.PanelRegistroBarberia getPanelRegBarberia() {
        return panelRegistroBarberia;
    }

    public static presentacion.PanelConfirmarCita getPanelConfirmarCita() {
        return panelConfirmarCita;
    }

    public static presentacion.PanelInfoBarberia getPanelInfoBarberia() {
        return panelInfoBarberia;
    }

    public static presentacion.PanelRegistroCuenta getPanelRegistroCuenta() {
        return panelRegistroCuenta;
    }

    public static presentacion.PanelPagoTarjeta getPanelPagoTarjeta() {
        return panelPagoTarjeta;
    }

    public static presentacion.PanelGestionarMisCitas getPanelGestionarMisCitas() {
        return panelGestionarMisCitas;
    }

    public static JFrame getFramePrincipal() {
        return framePrincipal;
    }
}