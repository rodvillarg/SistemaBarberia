/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dto.CitaDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.*;
import presentacion.controles.ControlVistas;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class PanelConfirmacion extends JPanel{
    private static final Color FONDO       = new Color(10, 10, 10);
    private static final Color CARD        = new Color(22, 22, 22);
    private static final Color BORDE       = new Color(55, 55, 55);
    private static final Color TEXTO       = new Color(241, 245, 249);
    private static final Color TEXTO_MUTED = new Color(148, 163, 184);
    private static final Color BTN_ORO    = new Color(212, 160, 23);
    private static final Color VERDE       = new Color(34, 197, 94);

    private JPanel panelDetalle;

    private final ControlVistas control;

    public PanelConfirmacion(ControlVistas control) {
        this.control = control;
        initUI();
    }

    private void initUI() {
        setBackground(FONDO);
        setLayout(new GridBagLayout());

        JPanel card = new presentacion.utilerias.PanelRedondeado(16, CARD, BORDE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(540, 500));

        // Top bar con logo
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(CARD);
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
        JLabel lblLogo = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        lblLogo.setForeground(TEXTO_MUTED);
        topBar.add(lblLogo, BorderLayout.EAST);

        // Icono éxito
        JPanel panelIcono = new JPanel(new GridBagLayout());
        panelIcono.setBackground(CARD);
        panelIcono.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        JLabel lblExito = new JLabel("Exito");
        lblExito.setBackground(VERDE);
        lblExito.setForeground(new Color(255, 255, 255));
        lblExito.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lblExito.setBorder(BorderFactory.createEmptyBorder(6, 20, 6, 20));
        lblExito.setOpaque(true);
        panelIcono.add(lblExito);

        JLabel lblTitulo = new JLabel("¡Su cita fue agendada con exito!");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        lblTitulo.setForeground(TEXTO);
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        JLabel lblSub = new JLabel("Recibirás un correo de confirmacion.");
        lblSub.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
        lblSub.setForeground(TEXTO_MUTED);
        lblSub.setAlignmentX(CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(BORDE);
        sep.setMaximumSize(new Dimension(500, 1));

        // Detalle de la cita
        panelDetalle = new JPanel(new GridLayout(0, 2, 10, 8));
        panelDetalle.setBackground(CARD);
        panelDetalle.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        panelDetalle.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        // Botones
        JButton btnOtra  = boton("Agendar otra cita");
        JButton btnMisCitas = botonSecundario("Ver mis citas");
        btnOtra.setAlignmentX(CENTER_ALIGNMENT);
        btnOtra.setMaximumSize(new Dimension(480, 42));
        btnMisCitas.setAlignmentX(CENTER_ALIGNMENT);

        btnOtra.addActionListener(e ->
                control.mostrar(ControlVistas.pantallaBarberias));
        btnMisCitas.addActionListener(e ->
                control.mostrar(ControlVistas.pantallaMisCitas));

        card.add(topBar);
        card.add(panelIcono);
        card.add(Box.createVerticalStrut(5));
        card.add(lblTitulo);
        card.add(lblSub);
        card.add(Box.createVerticalStrut(10));
        card.add(sep);
        card.add(panelDetalle);
        card.add(Box.createVerticalStrut(8));
        card.add(btnOtra);
        card.add(Box.createVerticalStrut(6));
        card.add(btnMisCitas);
        card.add(Box.createVerticalStrut(20));

        add(card);
    }

    private String numeroTarjetaMascarado = null;

    public void setNumeroTarjeta(String numero) {
        if (numero != null && numero.length() == 16) {
            this.numeroTarjetaMascarado = "**** **** **** " + numero.substring(12);
        } else {
            this.numeroTarjetaMascarado = null;
        }
    }

    public void mostrarCita(CitaDTO cita) {
        panelDetalle.removeAll();

        // Capturar la hora exacta en que se muestra la confirmacion
        String horaAgendado = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm")
                .format(new java.util.Date());
        String textoPago = "";
        if (dto.enums.MetodoPago.EFECTIVO.equals(cita.getMetodoPago())) {
            textoPago = "Efectivo";
        }
        String textoTarjeta = "";
        if (numeroTarjetaMascarado != null) {
            textoTarjeta = numeroTarjetaMascarado;
        }

        String[][] datos = {
            {"Nombre:",   cita.getCliente().getNombreCompleto()},
            {"Correo:",   cita.getCliente().getCorreo()},
            {"Barberia:", cita.getBarberia().getNombre()},
            {"Servicio:", cita.getServicio().getNombre()},
            {"Fecha y hora:", cita.getFechaHora()},
            {"Precio:",   "$" + String.format("%.2f", cita.getServicio().getPrecio())},
            {"Pago:",     textoPago},
            {"Tarjeta:",  textoTarjeta},
            {"Agendado:", horaAgendado}
        };
        for (String[] fila : datos) {
            if (fila[1].isBlank()) continue; // no iniciar filas vacias
            JLabel lbl = new JLabel(fila[0]);
            lbl.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
            lbl.setForeground(TEXTO_MUTED);
            JLabel val = new JLabel(fila[1]);
            val.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
            val.setForeground(TEXTO);
            panelDetalle.add(lbl);
            panelDetalle.add(val);
        }
        panelDetalle.revalidate();
        panelDetalle.repaint();

        // El correo de confirmacion se envia automaticamente dentro de AgendarCita
        // al momento de guardar la cita. No es necesario enviarlo aqui.
    }

    private JButton boton(String t) {
        JButton b = new JButton(t);
        b.setBackground(BTN_ORO); b.setForeground(TEXTO);
        b.setOpaque(true);
        b.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        b.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        b.setFocusPainted(false);
        b.setBorderPainted(false); b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JButton botonSecundario(String t) {
        JButton b = new JButton(t);
        b.setBackground(CARD); b.setForeground(TEXTO_MUTED);
        b.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        b.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

}
