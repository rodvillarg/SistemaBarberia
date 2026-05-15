/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dto.BarberiaDTO;
import dto.HorarioDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import itson.negocios_gestorbarberias.fachada.BarberiasFacade;
import itson.negocios_gestorbarberias.fachada.IBarberiasFacade;
import itson.negocios_gestorhorarios.fachada.HorariosFacade;
import itson.negocios_gestorhorarios.fachada.IHorariosFacade;
import itson.negocios_gestorresenas.fachada.IResenasFacade;
import itson.negocios_gestorresenas.fachada.ResenasFacade;
import itson.negocios_iniciosesion.IInicioSesionFachada;
import itson.negocios_iniciosesion.InicioSesionFachada;
import presentacion.controles.ControlVistas;
import presentacion.utilerias.GestorSesion;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class PanelSeleccionBarberia extends JPanel {
    
    private static final Color FONDO       = new Color(10, 10, 10);
    private static final Color CARD        = new Color(22, 22, 22);
    private static final Color BORDE       = new Color(55, 55, 55);
    private static final Color TEXTO       = new Color(241, 245, 249);
    private static final Color TEXTO_MUTED = new Color(148, 163, 184);
    private static final Color BTN_ORO    = new Color(212, 160, 23);
    private static final Color CARD_HOVER  = new Color(42, 42, 42);
    private static final Color VERDE       = new Color(34, 197, 94);
    private static final Color ROJO        = new Color(239, 68, 68);
    
    private final IInicioSesionFachada facadeLogin     = new InicioSesionFachada();
    private final IBarberiasFacade facadeBarberia = new BarberiasFacade();
    private final IHorariosFacade  facadeHorario  = new HorariosFacade();
    private final IResenasFacade   facadeResena   = new ResenasFacade();

    private JPanel panelGrid;
    private JLabel lblBienvenida;
    private List<BarberiaDTO> todasLasBarberias;

    private final ControlVistas control;

    public PanelSeleccionBarberia(ControlVistas control) {
        this.control = control;
        initUI();
    }

    private void initUI() {
        setBackground(FONDO);
        setLayout(new BorderLayout());

        // ── Top bar ──────────────────────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(15, 15, 15));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        izq.setBackground(new Color(15, 15, 15));

        // Buscador con patrón Observer — filtra en tiempo real sin botón
        presentacion.utilerias.PanelDeBuscador buscador = new presentacion.utilerias.PanelDeBuscador();
        buscador.setOnBusquedaChange(texto -> filtrarBarberias(texto));

        izq.add(buscador);

        JPanel der = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        der.setBackground(new Color(15, 15, 15));

        lblBienvenida = new JLabel();
        lblBienvenida.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        lblBienvenida.setForeground(TEXTO_MUTED);

        JButton btnMenuAdmin = botonHeader("Menu Principal");
        JButton btnMisCitas  = botonHeader("Mis Citas");
        JButton btnSalir     = botonHeader("Cerrar Sesion");

        der.add(lblBienvenida);
        der.add(btnMenuAdmin);
        der.add(btnMisCitas);
        der.add(btnSalir);

        JLabel lblLogo = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        lblLogo.setForeground(TEXTO);
        lblLogo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));

        topBar.add(izq, BorderLayout.WEST);
        topBar.add(lblLogo, BorderLayout.CENTER);
        topBar.add(der, BorderLayout.EAST);

        // ── Grid de barberias ─────────────────────────────────────────────────
        panelGrid = new JPanel(new WrapLayout(FlowLayout.LEFT, 15, 15));
        panelGrid.setBackground(FONDO);

        JScrollPane scroll = new JScrollPane(panelGrid);
        scroll.getViewport().setBackground(FONDO);
        scroll.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        scroll.setBackground(FONDO);
        scroll.getViewport().setOpaque(true);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(topBar, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // Acciones
        btnMenuAdmin.addActionListener(e ->
                control.mostrar(ControlVistas.pantallaMenuAdmin));
        btnMisCitas.addActionListener(e ->
                control.mostrar(ControlVistas.pantallaMisCitas));
        btnSalir.addActionListener(e -> {
            GestorSesion.cerrarSesion();
            control.mostrar(ControlVistas.pantallaLogin);
        });
        // Mostrar Menu Principal solo si es barbero
        addAncestorListener(new javax.swing.event.AncestorListener() {
            @Override public void ancestorAdded(javax.swing.event.AncestorEvent e) {
                boolean esBarbero = GestorSesion.haySesion() 
                                && facadeLogin.esBarbero(GestorSesion.getClienteActivo());
                btnMenuAdmin.setVisible(esBarbero);
                btnMisCitas.setVisible(!esBarbero);
                if (GestorSesion.haySesion())
                    lblBienvenida.setText("Bienvenido, "
                            + GestorSesion.getClienteActivo().getNombre());
            }
            @Override public void ancestorRemoved(javax.swing.event.AncestorEvent e) {}
            @Override public void ancestorMoved(javax.swing.event.AncestorEvent e)   {}
        });

        addAncestorListener(new javax.swing.event.AncestorListener() {
            @Override public void ancestorAdded(javax.swing.event.AncestorEvent e) {
                if (GestorSesion.haySesion())
                    lblBienvenida.setText("Bienvenido, " + GestorSesion.getClienteActivo().getNombre());
            }
            @Override public void ancestorRemoved(javax.swing.event.AncestorEvent e) {}
            @Override public void ancestorMoved(javax.swing.event.AncestorEvent e) {}
        });
    }

    public void cargarBarberias() {
        todasLasBarberias = facadeBarberia.obtenerBarberiasActivas();
        mostrarBarberias(todasLasBarberias);
    }

    private void filtrarBarberias(String texto) {
        if (todasLasBarberias == null) return;
        if (texto.isEmpty()) { mostrarBarberias(todasLasBarberias); return; }
        List<BarberiaDTO> filtradas = todasLasBarberias.stream()
                .filter(b -> b.getNombre().toLowerCase().contains(texto.toLowerCase())
                          || b.getDireccion().toLowerCase().contains(texto.toLowerCase()))
                .toList();
        mostrarBarberias(filtradas);
    }

    private void mostrarBarberias(List<BarberiaDTO> lista) {
        panelGrid.removeAll();
        if (lista.isEmpty()) {
            JLabel lbl = new JLabel("No se encontraron barberias.");
            lbl.setForeground(TEXTO_MUTED);
            lbl.setFont(new Font("Comic Sans MS", Font.ITALIC, 14));
            panelGrid.add(lbl);
        } else {
            for (BarberiaDTO b : lista) panelGrid.add(crearCard(b));
        }
        panelGrid.revalidate();
        panelGrid.repaint();
    }

    private JPanel crearCard(BarberiaDTO barberia) {
        presentacion.utilerias.PanelRedondeado card = new presentacion.utilerias.PanelRedondeado(14, CARD, BORDE);
        card.setPreferredSize(new Dimension(290, 200));
        card.setLayout(new BorderLayout());
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Imagen / banner superior
        JPanel banner = new JPanel(new GridBagLayout());
        banner.setBackground(new Color(42, 32, 0));
        banner.setPreferredSize(new Dimension(290, 90));
        JLabel icono = new JLabel("✂");
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        icono.setForeground(new Color(212, 160, 23));
        banner.add(icono);

        // Info inferior
        JPanel info = new JPanel();
        info.setBackground(CARD);
        info.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        // Badge ABIERTO/CERRADO
        List<HorarioDTO> horarios = facadeHorario.obtenerHorariosPorBarberia(barberia.getId());
        String diaHoyRaw = java.time.DayOfWeek.from(java.time.LocalDate.now())
                .getDisplayName(java.time.format.TextStyle.FULL, new java.util.Locale("es", "MX"))
                .toUpperCase();
        String diaHoy = java.text.Normalizer.normalize(diaHoyRaw,
                java.text.Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        HorarioDTO horarioHoy = null;
        for (HorarioDTO h : horarios) {
            if (diaHoy.equals(h.getDiaSemana())) { horarioHoy = h; break; }
        }
        boolean abierto = false;
        if (horarioHoy != null) {
            java.time.LocalTime ahora   = java.time.LocalTime.now();
            java.time.LocalTime apertura = java.time.LocalTime.parse(horarioHoy.getHoraApertura());
            java.time.LocalTime cierre   = java.time.LocalTime.parse(horarioHoy.getHoraCierre());
            abierto = !ahora.isBefore(apertura) && ahora.isBefore(cierre);
        }

        JPanel filaNombre = new JPanel(new BorderLayout(4, 0));
        filaNombre.setBackground(CARD);
        filaNombre.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 22));

        JLabel lblNombre = new JLabel(barberia.getNombre());
        lblNombre.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        lblNombre.setForeground(TEXTO);

        JLabel lblBadge = new JLabel(abierto ? "ABIERTO" : "CERRADO");
        lblBadge.setFont(new Font("Comic Sans MS", Font.BOLD, 9));
        lblBadge.setForeground(abierto ? VERDE : ROJO);
        lblBadge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(abierto ? VERDE : ROJO),
                BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        filaNombre.setAlignmentX(LEFT_ALIGNMENT);
        filaNombre.add(lblNombre, BorderLayout.WEST);
        filaNombre.add(lblBadge, BorderLayout.EAST);

        // Dirección truncada
        String dir = barberia.getDireccion();
        if (dir != null && dir.length() > 35) dir = dir.substring(0, 35) + "...";
        JLabel lblDir = new JLabel(dir);
        lblDir.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        lblDir.setForeground(TEXTO_MUTED);
        lblDir.setAlignmentX(LEFT_ALIGNMENT);

        // Estrellas
        double cal = facadeResena.calcularPromedio(barberia.getId());
        JLabel lblCal = new JLabel("★  " + String.format("%.1f", cal));
        lblCal.setFont(new Font("Dialog", Font.PLAIN, 14));
        lblCal.setForeground(new Color(250, 204, 21));
        lblCal.setAlignmentX(LEFT_ALIGNMENT);

        info.add(filaNombre);
        info.add(Box.createVerticalStrut(3));
        info.add(lblDir);
        info.add(Box.createVerticalStrut(4));
        info.add(lblCal);

        card.add(banner, BorderLayout.NORTH);
        card.add(info, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                control.<PanelInfoBarberia>getPanel(ControlVistas.pantallaInfoBarberia).cargarBarberia(barberia);
                control.mostrar(ControlVistas.pantallaInfoBarberia);
            }
            @Override public void mouseEntered(MouseEvent e) {
                card.setBackground(CARD_HOVER); info.setBackground(CARD_HOVER); card.repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                card.setBackground(CARD); info.setBackground(CARD); card.repaint();
            }
        });
        return card;
    }

    private JButton botonHeader(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(CARD); b.setForeground(TEXTO);
        b.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)));
        b.setFocusPainted(false);
        b.setBorderPainted(false); b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private static class WrapLayout extends FlowLayout {
        public WrapLayout(int align, int hgap, int vgap) { super(align, hgap, vgap); }
        @Override public Dimension preferredLayoutSize(Container t) { return layoutSize(t, true); }
        @Override public Dimension minimumLayoutSize(Container t)   { return layoutSize(t, false); }
        private Dimension layoutSize(Container target, boolean pref) {
            synchronized (target.getTreeLock()) {
                int w = target.getSize().width; if (w == 0) w = Integer.MAX_VALUE;
                int hg = getHgap(), vg = getVgap();
                Insets ins = target.getInsets();
                int max = w - (ins.left + ins.right + hg * 2);
                Dimension dim = new Dimension(0, 0);
                int rw = 0, rh = 0;
                for (int i = 0; i < target.getComponentCount(); i++) {
                    Component m = target.getComponent(i);
                    if (m.isVisible()) {
                        Dimension d = pref ? m.getPreferredSize() : m.getMinimumSize();
                        if (rw + d.width > max) { dim.width = Math.max(dim.width, rw); dim.height += rh + vg; rw = 0; rh = 0; }
                        rw += d.width + hg; rh = Math.max(rh, d.height);
                    }
                }
                dim.width = Math.max(dim.width, rw);
                dim.height += rh + ins.top + ins.bottom + vg * 2;
                return dim;
            }
        }
    }

}
