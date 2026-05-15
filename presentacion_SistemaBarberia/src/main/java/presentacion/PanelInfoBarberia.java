/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dto.BarberiaDTO;
import dto.HorarioDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import itson.negocios_gestorhorarios.fachada.HorariosFacade;
import itson.negocios_gestorhorarios.fachada.IHorariosFacade;
import itson.negocios_gestorresenas.fachada.IResenasFacade;
import itson.negocios_gestorresenas.fachada.ResenasFacade;
import presentacion.controles.ControlVistas;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class PanelInfoBarberia extends JPanel {
    
    private static final Color FONDO       = new Color(10, 10, 10);
    private static final Color CARD        = new Color(22, 22, 22);
    private static final Color BORDE       = new Color(55, 55, 55);
    private static final Color TEXTO       = new Color(241, 245, 249);
    private static final Color TEXTO_MUTED = new Color(148, 163, 184);
    private static final Color BTN_ORO     = new Color(212, 160, 23);
    private static final Color VERDE       = new Color(34, 197, 94);
    private static final Color ROJO        = new Color(239, 68, 68);
    private static final Color BANNER      = new Color(42, 32, 0);
    
    private final IHorariosFacade facadeHorario = new HorariosFacade();
    private final IResenasFacade  facadeResena  = new ResenasFacade();

    private JLabel      lblNombre;
    private JLabel      lblEstado;
    private JLabel      lblDireccion;
    private JLabel      lblDescripcion;
    private JLabel      lblCalificacion;
    private JLabel      lblHorarioCard;
    private JLabel      lblTelefonoCard;
    private JPanel      panelHorarios;
    private BarberiaDTO barberiaActual;

    private final ControlVistas control;

    public PanelInfoBarberia(ControlVistas control) {
        this.control = control;
        initUI();
    }

    private void initUI() {
        setBackground(FONDO);
        setLayout(new BorderLayout());

        // ── Top bar ───────────────────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(15, 15, 15));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JLabel lblLogo = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        lblLogo.setForeground(TEXTO_MUTED);
        topBar.add(lblLogo, BorderLayout.EAST);

        // ── Contenido ─────────────────────────────────────────────────────
        JPanel contenido = new JPanel();
        contenido.setBackground(FONDO);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));

        // Banner
        JPanel banner = new JPanel(new GridBagLayout());
        banner.setBackground(BANNER);
        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        banner.setPreferredSize(new Dimension(0, 90));
        JLabel icono = new JLabel("✂");
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 44));
        icono.setForeground(BTN_ORO);
        banner.add(icono);

        // ── Sección principal (padding lateral) ───────────────────────────
        JPanel seccion = new JPanel();
        seccion.setBackground(FONDO);
        seccion.setLayout(new BoxLayout(seccion, BoxLayout.Y_AXIS));
        seccion.setBorder(BorderFactory.createEmptyBorder(12, 16, 10, 16));

        // Fila: nombre + badge estado
        JPanel filaHeader = new JPanel(new BorderLayout(8, 0));
        filaHeader.setBackground(FONDO);
        filaHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        filaHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        lblNombre = new JLabel("Barberia");
        lblNombre.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lblNombre.setForeground(TEXTO);

        lblEstado = new JLabel("CERRADO");
        lblEstado.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
        lblEstado.setForeground(ROJO);
        lblEstado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROJO),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)));

        filaHeader.add(lblNombre, BorderLayout.WEST);
        filaHeader.add(lblEstado, BorderLayout.EAST);

        // Cards info
        JPanel cardsInfo = new JPanel(new GridLayout(1, 3, 6, 0));
        cardsInfo.setBackground(FONDO);
        cardsInfo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        cardsInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JPanel cardHorario = crearCard("HORARIO",  "---", false);
        JPanel cardTel     = crearCard("TELÉFONO", "---",           false);
        JPanel cardCal     = crearCard("CALIF.",   "0.0",           true);

        lblHorarioCard  = getLabelValor(cardHorario);
        lblTelefonoCard = getLabelValor(cardTel);
        lblCalificacion = getLabelValor(cardCal);
        lblCalificacion.setFont(new Font("Dialog", Font.BOLD, 12));
        lblCalificacion.setForeground(BTN_ORO);

        cardsInfo.add(cardHorario);
        cardsInfo.add(cardTel);
        cardsInfo.add(cardCal);

        // Dirección
        lblDireccion = new JLabel("...");
        lblDireccion.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        lblDireccion.setForeground(TEXTO_MUTED);
        JPanel wrapDir = new JPanel(new BorderLayout());
        wrapDir.setBackground(FONDO);
        wrapDir.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        wrapDir.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        wrapDir.add(lblDireccion, BorderLayout.WEST);

        // Descripción
        lblDescripcion = new JLabel("<html><p>...</p></html>");
        lblDescripcion.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        lblDescripcion.setForeground(new Color(241, 245, 249));
        JPanel wrapDesc = new JPanel(new BorderLayout());
        wrapDesc.setBackground(FONDO);
        wrapDesc.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        wrapDesc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        wrapDesc.add(lblDescripcion, BorderLayout.CENTER);

        // Panel días de la semana
        panelHorarios = new JPanel(new BorderLayout());
        panelHorarios.setBackground(FONDO);

        // Boton ver reseñas
        JButton btnResenas = new JButton("Ver Reseñas");
        btnResenas.setBackground(FONDO);
        btnResenas.setForeground(TEXTO_MUTED);
        btnResenas.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
        btnResenas.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(6, 14, 6, 14)));
        btnResenas.setFocusPainted(false);
        btnResenas.setBorderPainted(false);
        btnResenas.setOpaque(true);
        btnResenas.setAlignmentX(LEFT_ALIGNMENT);
        btnResenas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnResenas.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                control.<PanelResenas>getPanel(ControlVistas.pantallaResenas)
                        .setBarberia(barberiaActual);
                control.mostrar(ControlVistas.pantallaResenas);
            }
        });

        seccion.add(filaHeader);
        seccion.add(cardsInfo);
        seccion.add(wrapDir);
        seccion.add(wrapDesc);
        seccion.add(panelHorarios);
        JPanel wrapResenas = new JPanel(new BorderLayout());
        wrapResenas.setBackground(FONDO);
        wrapResenas.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        wrapResenas.add(btnResenas, BorderLayout.WEST);
        seccion.add(wrapResenas);

        contenido.add(banner);
        contenido.add(seccion);
        contenido.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(contenido,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBackground(FONDO);
        scroll.getViewport().setBackground(FONDO);
        scroll.setOpaque(true);

        // ── Footer ────────────────────────────────────────────────────────
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(15, 15, 15));
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(55, 55, 55)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        JButton btnVolver = botonSecundario("Regresar");
        btnVolver.addActionListener(e ->
                control.mostrar(ControlVistas.pantallaBarberias));

        JButton btnApartar = new JButton("Seleccionar Servicio");
        btnApartar.setBackground(BTN_ORO);
        btnApartar.setForeground(new Color(15, 15, 15));
        btnApartar.setOpaque(true);
        btnApartar.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        btnApartar.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        btnApartar.setFocusPainted(false);
        btnApartar.setBorderPainted(false);
        btnApartar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnApartar.addActionListener(e -> {
            control.<PanelSeleccionServicio>getPanel(ControlVistas.pantallaServicios)
                    .setPantallaOrigen(ControlVistas.pantallaInfoBarberia);
            control.<PanelSeleccionServicio>getPanel(ControlVistas.pantallaServicios)
                    .cargarServicios(barberiaActual);
            control.mostrar(ControlVistas.pantallaServicios);
        });

        footer.add(btnVolver,  BorderLayout.WEST);
        footer.add(btnApartar, BorderLayout.EAST);

        add(topBar,  BorderLayout.NORTH);
        add(scroll,  BorderLayout.CENTER);
        add(footer,  BorderLayout.SOUTH);
    }

    private JPanel crearCard(String titulo, String valor, boolean dorado) {
        presentacion.utilerias.PanelRedondeado card =
                new presentacion.utilerias.PanelRedondeado(6, CARD, BORDE);
        card.setLayout(new BorderLayout(0, 3));
        card.setBorder(BorderFactory.createEmptyBorder(7, 8, 7, 8));

        JLabel lblTit = new JLabel(titulo, SwingConstants.CENTER);
        lblTit.setFont(new Font("Comic Sans MS", Font.PLAIN, 9));
        lblTit.setForeground(TEXTO_MUTED);

        JPanel valPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        valPanel.setBackground(CARD);
        valPanel.setOpaque(false);
        JLabel lblVal = new JLabel(valor, SwingConstants.CENTER);
        lblVal.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
        lblVal.setForeground(dorado ? BTN_ORO : TEXTO);
        valPanel.add(lblVal);

        card.add(lblTit,   BorderLayout.NORTH);
        card.add(valPanel, BorderLayout.CENTER);
        return card;
    }

    private JLabel getLabelValor(JPanel card) {
        return (JLabel) ((JPanel) card.getComponent(1)).getComponent(0);
    }

    public void cargarBarberia(BarberiaDTO barberia) {
        this.barberiaActual = barberia;
        lblNombre.setText(barberia.getNombre());
        lblTelefonoCard.setText(barberia.getTelefono());
        lblDireccion.setText(barberia.getDireccion());
        if (barberia.getDescripcion() != null && !barberia.getDescripcion().isEmpty()) {
            lblDescripcion.setText("<html><p style='width:400px'>" + barberia.getDescripcion() + "</p></html>");
        }
        actualizarEstado(barberia.getId());
        revalidate();
        repaint();
    }

    private void actualizarEstado(String barberiaId) {
        List<HorarioDTO> horarios = facadeHorario
                        .obtenerHorariosPorBarberia(barberiaId);

        String diaHoyRaw = DayOfWeek.from(java.time.LocalDate.now())
                .getDisplayName(TextStyle.FULL, new Locale("es", "MX"))
                .toUpperCase();
        String diaHoy = java.text.Normalizer
                .normalize(diaHoyRaw, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        HorarioDTO horarioHoy = null;
        for (HorarioDTO h : horarios) {
            if (diaHoy.equals(h.getDiaSemana())) { horarioHoy = h; break; }
        }

        // Badge estado
        if (horarioHoy == null) {
            lblEstado.setText("CERRADO");
            lblEstado.setForeground(ROJO);
            lblEstado.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ROJO),
                    BorderFactory.createEmptyBorder(2, 6, 2, 6)));
        } else {
            LocalTime ahora    = LocalTime.now();
            LocalTime apertura = LocalTime.parse(horarioHoy.getHoraApertura());
            LocalTime cierre   = LocalTime.parse(horarioHoy.getHoraCierre());
            boolean abierto    = !ahora.isBefore(apertura) && ahora.isBefore(cierre);
            if (abierto) {
                lblEstado.setText("ABIERTO");
                lblEstado.setForeground(VERDE);
                lblEstado.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(VERDE),
                        BorderFactory.createEmptyBorder(2, 6, 2, 6)));
            } else {
                lblEstado.setText("CERRADO");
                lblEstado.setForeground(ROJO);
                lblEstado.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ROJO),
                        BorderFactory.createEmptyBorder(2, 6, 2, 6)));
            }
            lblHorarioCard.setText(horarioHoy.getHoraApertura()
                    + "-" + horarioHoy.getHoraCierre());
        }

        // Calificacion
        if (barberiaActual != null) {
            lblCalificacion.setText("★ " + String.format("%.1f",
                    facadeResena.calcularPromedio(barberiaActual.getId())));
        }

        // Días de la semana
        panelHorarios.removeAll();
        String[] orden   = {"LUNES","MARTES","MIERCOLES","JUEVES","VIERNES","SABADO","DOMINGO"};
        String[] nombres = {"Lu","Ma","Mi","Ju","Vi","Sa","Do"};

        JPanel filaDias = new JPanel(new GridLayout(1, 7, 4, 0));
        filaDias.setBackground(FONDO);
        filaDias.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        for (int i = 0; i < orden.length; i++) {
            HorarioDTO h = null;
            for (HorarioDTO hh : horarios) {
                if (orden[i].equals(hh.getDiaSemana())) { h = hh; break; }
            }
            boolean abre = h != null;

            presentacion.utilerias.PanelRedondeado diaCard =
                    new presentacion.utilerias.PanelRedondeado(4, CARD, BORDE);
            diaCard.setLayout(new BorderLayout(0, 2));
            diaCard.setBorder(BorderFactory.createEmptyBorder(5, 3, 5, 3));

            JLabel lblDia = new JLabel(nombres[i], SwingConstants.CENTER);
            lblDia.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
            lblDia.setForeground(TEXTO_MUTED);

            String horarioTexto = "—";
            if (abre) {
                horarioTexto = h.getHoraApertura() + "-" + h.getHoraCierre();
            }
            JLabel lblHr = new JLabel(horarioTexto, SwingConstants.CENTER);
            lblHr.setFont(new Font("Comic Sans MS", Font.PLAIN, 8));
            lblHr.setForeground(abre ? TEXTO : BORDE);

            diaCard.add(lblDia, BorderLayout.NORTH);
            diaCard.add(lblHr,  BorderLayout.CENTER);
            filaDias.add(diaCard);
        }

        panelHorarios.add(filaDias, BorderLayout.NORTH);
        panelHorarios.revalidate();
        panelHorarios.repaint();
    }

    private JButton botonSecundario(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(CARD);
        b.setForeground(TEXTO);
        b.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}
