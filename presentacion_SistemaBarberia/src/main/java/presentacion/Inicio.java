/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package presentacion;

import dto.ClienteDTO;
import java.awt.Color;
import javax.swing.SwingUtilities;
import javax.swing.*;
import presentacion.controles.ControlInsertMasivo;
import presentacion.controles.ControlVistas;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class Inicio {

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) {

        ControlInsertMasivo.insertMasivo();

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                UIManager.put("Button.select",     new Color(180, 130, 10));
                UIManager.put("control",           new Color(22, 22, 22));
                UIManager.put("Button.background", new Color(22, 22, 22));
                UIManager.put("Button.foreground", new Color(241, 245, 249));
            } catch (Exception ignored) {}

            PanelInicioSesion       panelLogin        = new PanelInicioSesion();
            PanelRegistro           panelRegistro     = new PanelRegistro();
            PanelSeleccionBarberia  panelBarberias    = new PanelSeleccionBarberia();
            PanelSeleccionServicio  panelServicios    = new PanelSeleccionServicio();
            PanelSeleccionFechaHora panelFechaHora    = new PanelSeleccionFechaHora();
            PanelConfirmacion       panelConfirmacion = new PanelConfirmacion();
            PanelMisCitas           panelMisCitas     = new PanelMisCitas();
            PanelMenuAdmin          panelMenuAdmin    = new PanelMenuAdmin(new ClienteDTO());
            PanelGestionCitas       panelGestionCitas = new PanelGestionCitas();
            PanelAdminGestionCitas  panelAdminGestion = new PanelAdminGestionCitas();
            PanelRegistroBarberia   panelRegBarberia  = new PanelRegistroBarberia();
            PanelConfirmarCita      panelConfirmarCita = new PanelConfirmarCita();
            PanelInfoBarberia       panelInfoBarberia  = new PanelInfoBarberia();
            PanelRegistroCuenta     panelRegCuenta     = new PanelRegistroCuenta();
            PanelPagoTarjeta        panelPagoTarjeta   = new PanelPagoTarjeta();
            PanelResenas            panelResenas       = new PanelResenas();
            PanelGestionarMisCitas  panelGestionarMisCitas = new PanelGestionarMisCitas();
            PanelAsignarHorario panelAsignarHorario = new PanelAsignarHorario();

            ControlVistas.registrarPanel(panelLogin);
            ControlVistas.registrarPanel(panelRegistro);
            ControlVistas.registrarPanel(panelBarberias);
            ControlVistas.registrarPanel(panelServicios);
            ControlVistas.registrarPanel(panelFechaHora);
            ControlVistas.registrarPanel(panelConfirmacion);
            ControlVistas.registrarPanel(panelMisCitas);
            ControlVistas.registrarPanel(panelMenuAdmin);
            ControlVistas.registrarPanel(panelGestionCitas);
            ControlVistas.registrarPanel(panelAdminGestion);
            ControlVistas.registrarPanel(panelRegBarberia);
            ControlVistas.registrarPanel(panelConfirmarCita);
            ControlVistas.registrarPanel(panelInfoBarberia);
            ControlVistas.registrarPanel(panelRegCuenta);
            ControlVistas.registrarPanel(panelPagoTarjeta);
            ControlVistas.registrarPanel(panelResenas);
            ControlVistas.registrarPanel(panelGestionarMisCitas);
            ControlVistas.registrarPanel(panelAsignarHorario);

            ControlVistas.mostrar(ControlVistas.pantallaLogin);
            ControlVistas.iniciar();

            panelBarberias.addAncestorListener(new javax.swing.event.AncestorListener() {
                @Override public void ancestorAdded(javax.swing.event.AncestorEvent e)   { panelBarberias.cargarBarberias(); }
                @Override public void ancestorRemoved(javax.swing.event.AncestorEvent e) {}
                @Override public void ancestorMoved(javax.swing.event.AncestorEvent e)   {}
            });
            panelMisCitas.addAncestorListener(new javax.swing.event.AncestorListener() {
                @Override public void ancestorAdded(javax.swing.event.AncestorEvent e)   { panelMisCitas.cargarCitas(); }
                @Override public void ancestorRemoved(javax.swing.event.AncestorEvent e) {}
                @Override public void ancestorMoved(javax.swing.event.AncestorEvent e)   {}
            });
            panelGestionCitas.addAncestorListener(new javax.swing.event.AncestorListener() {
                @Override public void ancestorAdded(javax.swing.event.AncestorEvent e) {
                    if (presentacion.utilerias.GestorSesion.haySesion()) {
                        panelGestionCitas.cargarCitas(
                                presentacion.utilerias.GestorSesion.getClienteActivo());
                    }
                }
                @Override public void ancestorRemoved(javax.swing.event.AncestorEvent e) {}
                @Override public void ancestorMoved(javax.swing.event.AncestorEvent e)   {}
            });
        });
    }
}