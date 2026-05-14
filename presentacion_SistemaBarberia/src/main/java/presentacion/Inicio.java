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
 * @author Jesus Rodrigo Villegas - 261186
 */
public class Inicio {

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

            ControlVistas control = new ControlVistas();

            PanelInicioSesion       panelLogin             = new PanelInicioSesion(control);
            PanelRegistro           panelRegistro          = new PanelRegistro(control);
            PanelSeleccionBarberia  panelBarberias         = new PanelSeleccionBarberia(control);
            PanelSeleccionServicio  panelServicios         = new PanelSeleccionServicio(control);
            PanelSeleccionFechaHora panelFechaHora         = new PanelSeleccionFechaHora(control);
            PanelConfirmacion       panelConfirmacion      = new PanelConfirmacion(control);
            PanelMisCitas           panelMisCitas          = new PanelMisCitas(control);
            PanelMenuAdmin          panelMenuAdmin         = new PanelMenuAdmin(control);
            PanelGestionarCitas     panelGestionCitas      = new PanelGestionarCitas(control);
            PanelRegistroBarberia   panelRegBarberia       = new PanelRegistroBarberia(control);
            PanelConfirmarCita      panelConfirmarCita     = new PanelConfirmarCita(control);
            PanelInfoBarberia       panelInfoBarberia      = new PanelInfoBarberia(control);
            PanelRegistroCuenta     panelRegCuenta         = new PanelRegistroCuenta(control);
            PanelPagoTarjeta        panelPagoTarjeta       = new PanelPagoTarjeta(control);
            PanelResenas            panelResenas           = new PanelResenas(control);
            PanelAsignarHorario     panelAsignarHorario    = new PanelAsignarHorario(control);
            PanelGestionarServicios panelGestionarServicios = new PanelGestionarServicios(control);

            control.registrarPanel(ControlVistas.pantallaLogin,           panelLogin);
            control.registrarPanel(ControlVistas.pantallaRegistro,        panelRegistro);
            control.registrarPanel(ControlVistas.pantallaBarberias,       panelBarberias);
            control.registrarPanel(ControlVistas.pantallaServicios,       panelServicios);
            control.registrarPanel(ControlVistas.pantallaFechaHora,      panelFechaHora);
            control.registrarPanel(ControlVistas.pantallaConfirmacion,    panelConfirmacion);
            control.registrarPanel(ControlVistas.pantallaMisCitas,       panelMisCitas);
            control.registrarPanel(ControlVistas.pantallaMenuAdmin,      panelMenuAdmin);
            control.registrarPanel(ControlVistas.pantallaGestionarCitas, panelGestionCitas);
            control.registrarPanel(ControlVistas.pantallaRegBarberia,    panelRegBarberia);
            control.registrarPanel(ControlVistas.pantallaConfirmarCita,  panelConfirmarCita);
            control.registrarPanel(ControlVistas.pantallaInfoBarberia,   panelInfoBarberia);
            control.registrarPanel(ControlVistas.pantallaRegistroCuenta, panelRegCuenta);
            control.registrarPanel(ControlVistas.pantallaPagoTarjeta,    panelPagoTarjeta);
            control.registrarPanel(ControlVistas.pantallaResenas,         panelResenas);
            control.registrarPanel(ControlVistas.pantallaAsignarHorario, panelAsignarHorario);
            control.registrarPanel(ControlVistas.pantallaGestionarServicios, panelGestionarServicios);
            
            control.mostrar(ControlVistas.pantallaLogin);
            control.iniciar();

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
            
            panelMenuAdmin.addAncestorListener(new javax.swing.event.AncestorListener() {
                @Override
                public void ancestorAdded(javax.swing.event.AncestorEvent e) {
                    if (presentacion.utilerias.GestorSesion.haySesion()) {
                        panelMenuAdmin.actualizarAdmin(
                                presentacion.utilerias.GestorSesion.getClienteActivo());
                    }
                }

                @Override
                public void ancestorRemoved(javax.swing.event.AncestorEvent e) {
                }

                @Override
                public void ancestorMoved(javax.swing.event.AncestorEvent e) {
                }
            });
            
            panelGestionCitas.addAncestorListener(new javax.swing.event.AncestorListener() {
                @Override
                public void ancestorAdded(javax.swing.event.AncestorEvent e) {
                    if (presentacion.utilerias.GestorSesion.haySesion()) {
                        panelGestionCitas.cargarCitas();
                    }
                }

                @Override
                public void ancestorRemoved(javax.swing.event.AncestorEvent e) {
                }

                @Override
                public void ancestorMoved(javax.swing.event.AncestorEvent e) {
                }
            });

            panelGestionarServicios.addAncestorListener(new javax.swing.event.AncestorListener() {
                @Override
                public void ancestorAdded(javax.swing.event.AncestorEvent e) {
                    panelGestionarServicios.refrescar();
                }

                @Override
                public void ancestorRemoved(javax.swing.event.AncestorEvent e) {
                }

                @Override
                public void ancestorMoved(javax.swing.event.AncestorEvent e) {
                }
            });

            panelAsignarHorario.addAncestorListener(new javax.swing.event.AncestorListener() {
                @Override
                public void ancestorAdded(javax.swing.event.AncestorEvent e) {
                    panelAsignarHorario.refrescar();
                }

                @Override
                public void ancestorRemoved(javax.swing.event.AncestorEvent e) {
                }

                @Override
                public void ancestorMoved(javax.swing.event.AncestorEvent e) {
                }
            });
        });
    }
}