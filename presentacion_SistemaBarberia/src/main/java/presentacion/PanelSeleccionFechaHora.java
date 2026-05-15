/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dto.CitaDTO;
import dto.HorarioDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;
import javax.swing.BorderFactory;
import javax.swing.*;
import itson.negocios_gestorcitas.fachada.CitasFacade;
import itson.negocios_gestorcitas.fachada.ICitasFacade;
import itson.negocios_gestorhorarios.fachada.HorariosFacade;
import itson.negocios_gestorhorarios.fachada.IHorariosFacade;
import presentacion.controles.ControlVistas;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class PanelSeleccionFechaHora extends JPanel {

    private static final Color FONDO       = new Color(10, 10, 10);
    private static final Color CARD        = new Color(22, 22, 22);
    private static final Color BORDE       = new Color(55, 55, 55);
    private static final Color TEXTO       = new Color(241, 245, 249);
    private static final Color TEXTO_MUTED = new Color(148, 163, 184);
    private static final Color BTN_ORO     = new Color(212, 160, 23);
    private static final Color DIA_HOY     = new Color(60, 60, 60);
    private static final Color DIA_SEL     = new Color(180, 130, 10);
    private static final Color DIA_BLOQ    = new Color(15, 15, 15);
    private static final Color DIA_NORMAL  = new Color(40, 40, 40);
    private static final Color DIA_HOVER   = new Color(65, 85, 130);
    private static final Color SLOT_NORMAL = new Color(40, 40, 40);
    private static final Color SLOT_SEL    = new Color(212, 160, 23);
    
    private final IHorariosFacade facadeHorario = new HorariosFacade();
    private final ICitasFacade    facadeCita    = new CitasFacade();
    
    private static final String[] DIAS_SEM = {"Lu", "Ma", "Mi", "Ju", "Vi", "Sa", "Do"};

    // Estado
    private CitaDTO   citaEnProceso;
    private LocalDate fechaSeleccionada = LocalDate.now();
    private LocalDate fechaAnterior     = null;
    private YearMonth mesActual         = YearMonth.now();
    private String    horaSeleccionada  = null;

    // Referencias UI
    private JPanel          panelCalendario;
    private JLabel          lblMesAnio;
    private JLabel          lblResumen;
    private JButton         btnConfirmar;
    private final List<JButton> slots = new ArrayList<>();
    private JPanel          gridSlots;

    private final ControlVistas control;

    public PanelSeleccionFechaHora(ControlVistas control) {
        this.control = control;
        initUI();
    }

    private void initUI() {
        setBackground(FONDO);
        setLayout(new BorderLayout());

        // ── Top bar ───────────────────────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(15, 15, 15));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblTitulo = new JLabel("SELECCIONAR FECHA Y HORA");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        lblTitulo.setForeground(TEXTO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblLogo = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        lblLogo.setForeground(TEXTO_MUTED);

        topBar.add(lblTitulo, BorderLayout.CENTER);
        topBar.add(lblLogo, BorderLayout.EAST);

        // ── Centro: calendario + slots ────────────────────────────────────────
        JPanel centro = new JPanel(new GridLayout(1, 2, 15, 0));
        centro.setBackground(FONDO);
        centro.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
        centro.add(construirPanelCalendario());
        centro.add(construirPanelSlots());

        // ── Footer ────────────────────────────────────────────────────────────
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(15, 15, 15));
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(55, 55, 55)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        lblResumen = new JLabel("Selecciona una fecha y un horario para continuar");
        lblResumen.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        lblResumen.setForeground(TEXTO_MUTED);

        btnConfirmar = new JButton("Confirmar Cita");
        btnConfirmar.setBackground(BTN_ORO);
        btnConfirmar.setOpaque(true);
        btnConfirmar.setForeground(new Color(15, 15, 15));
        btnConfirmar.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        btnConfirmar.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setBorderPainted(false);
        btnConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnConfirmar.addActionListener(e -> confirmarCita());

        JButton btnVolver = botonHeader("Regresar");
        btnVolver.addActionListener(e ->
                control.mostrar(ControlVistas.pantallaServicios));

        footer.add(btnVolver, BorderLayout.WEST);
        footer.add(lblResumen, BorderLayout.CENTER);
        footer.add(btnConfirmar, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }

    // ── Calendario ────────────────────────────────────────────────────────────

    private JPanel construirPanelCalendario() {
        presentacion.utilerias.PanelRedondeado panel =
                new presentacion.utilerias.PanelRedondeado(12, CARD, BORDE);
        panel.setLayout(new BorderLayout(0, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel lbl = new JLabel("SELECCIONA LA FECHA");
        lbl.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        lbl.setForeground(TEXTO);

        JPanel navMes = new JPanel(new BorderLayout());
        navMes.setBackground(CARD);

        JButton btnAnt = botonNav("<");
        JButton btnSig = botonNav(">");
        lblMesAnio = new JLabel("", SwingConstants.CENTER);
        lblMesAnio.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        lblMesAnio.setForeground(TEXTO);

        btnAnt.addActionListener(e -> {
            YearMonth anterior = mesActual.minusMonths(1);
            if (!anterior.isBefore(YearMonth.now())) {
                mesActual = anterior;
                actualizarCalendario();
            }
        });
        btnSig.addActionListener(e -> {
            mesActual = mesActual.plusMonths(1);
            actualizarCalendario();
        });

        navMes.add(btnAnt, BorderLayout.WEST);
        navMes.add(lblMesAnio, BorderLayout.CENTER);
        navMes.add(btnSig, BorderLayout.EAST);

        panelCalendario = new JPanel(new GridLayout(0, 7, 4, 4));
        panelCalendario.setBackground(CARD);

        panel.add(lbl, BorderLayout.NORTH);
        JPanel contenedor = new JPanel(new BorderLayout(0, 6));
        contenedor.setBackground(CARD);
        contenedor.add(navMes, BorderLayout.NORTH);
        contenedor.add(panelCalendario, BorderLayout.CENTER);
        panel.add(contenedor, BorderLayout.CENTER);

        actualizarCalendario();
        return panel;
    }

    private void actualizarCalendario() {
        String nombreMes = mesActual.getMonth()
                .getDisplayName(TextStyle.FULL, new Locale("es", "MX"));
        lblMesAnio.setText(nombreMes.toUpperCase() + "  " + mesActual.getYear());

        panelCalendario.removeAll();

        for (String d : DIAS_SEM) {
            JLabel lbl = new JLabel(d, SwingConstants.CENTER);
            lbl.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
            lbl.setForeground(TEXTO_MUTED);
            panelCalendario.add(lbl);
        }

        LocalDate hoy       = LocalDate.now();
        LocalDate minFecha  = hoy;
        LocalDate primerDia = mesActual.atDay(1);
        int offset = primerDia.getDayOfWeek().getValue() - 1;

        for (int i = 0; i < offset; i++) {
            panelCalendario.add(new JLabel(""));
        }

        int diasEnMes = mesActual.lengthOfMonth();
        for (int d = 1; d <= diasEnMes; d++) {
            LocalDate dia = mesActual.atDay(d);
            JButton btn = new JButton(String.valueOf(d));
            btn.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setPreferredSize(new Dimension(34, 35));

            if (dia.isBefore(minFecha)) {
                // Día pasado — bloqueado
                btn.setBackground(DIA_BLOQ);
                btn.setForeground(new Color(45, 55, 80));
                btn.setEnabled(false);
                btn.setCursor(Cursor.getDefaultCursor());

            } else if (dia.equals(fechaSeleccionada)) {
                // Seleccionado — prioridad sobre hoy
                btn.setBackground(DIA_SEL);
                btn.setForeground(new Color(15, 15, 15));
                btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                btn.setBorderPainted(true);
                btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btn.addActionListener(e -> {
                    fechaAnterior     = fechaSeleccionada;
                    fechaSeleccionada = dia;
                    limpiarHoraSiCambioFecha();
                    actualizarCalendario();
                    actualizarSlots();
                    actualizarResumen();
                });

            } else {
                // Disponible — hoy sin seleccionar o días futuros
                Color bgNormal;
                if (dia.equals(hoy)) {
                    bgNormal = DIA_HOY;
                } else {
                    bgNormal = DIA_NORMAL;
                }
                btn.setBackground(bgNormal);
                btn.setForeground(TEXTO);
                btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                btn.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        if (btn.isEnabled()) btn.setBackground(DIA_HOVER);
                    }
                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        if (!dia.equals(fechaSeleccionada))
                            btn.setBackground(bgNormal);
                    }
                });
                btn.addActionListener(e -> {
                    fechaAnterior     = fechaSeleccionada;
                    fechaSeleccionada = dia;
                    limpiarHoraSiCambioFecha();
                    actualizarCalendario();
                    actualizarSlots();
                    actualizarResumen();
                });
            }

            panelCalendario.add(btn);
        }

        panelCalendario.revalidate();
        panelCalendario.repaint();
    }

    /**
     * Limpia la hora seleccionada si el usuario cambió de fecha.
     * Corrige el bug donde volver a la misma fecha dejaba la hora
     * visualmente deseleccionada pero aún registrada en el sistema.
     */
    private void limpiarHoraSiCambioFecha() {
        if (fechaAnterior == null || !fechaAnterior.equals(fechaSeleccionada)) {
            horaSeleccionada = null;
        }
    }

    // ── Slots ─────────────────────────────────────────────────────────────────

    private JPanel construirPanelSlots() {
        presentacion.utilerias.PanelRedondeado panel =
                new presentacion.utilerias.PanelRedondeado(12, CARD, BORDE);
        panel.setLayout(new BorderLayout(0, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel lbl = new JLabel("SELECCIONA EL HORARIO");
        lbl.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        lbl.setForeground(TEXTO);

        gridSlots = new JPanel(new GridLayout(0, 4, 8, 8));
        gridSlots.setBackground(CARD);

        generarSlots(java.time.LocalTime.of(9, 0), java.time.LocalTime.of(18, 0));

        JScrollPane scroll = new JScrollPane(gridSlots,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBackground(CARD);
        scroll.getViewport().setBackground(CARD);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setOpaque(true);

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private void generarSlots(java.time.LocalTime apertura, java.time.LocalTime cierre) {
        slots.clear();
        gridSlots.removeAll();
        java.time.LocalTime t = apertura;
        while (t.isBefore(cierre)) {
            String hora = String.format("%02d:%02d", t.getHour(), t.getMinute());
            JButton btn = new JButton(hora);
            btn.setBackground(SLOT_NORMAL);
            btn.setForeground(TEXTO);
            btn.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
            btn.setBorder(BorderFactory.createLineBorder(BORDE));
            btn.setFocusPainted(false);
            btn.setBorderPainted(true);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.addActionListener(e -> seleccionarHora(hora, btn));
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (btn.isEnabled() && !hora.equals(horaSeleccionada))
                        btn.setBackground(DIA_HOVER);
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (btn.isEnabled() && !hora.equals(horaSeleccionada))
                        btn.setBackground(SLOT_NORMAL);
                }
            });
            slots.add(btn);
            gridSlots.add(btn);
            t = t.plusMinutes(30);
        }
        // Restaurar visual si ya hay una hora seleccionada
        if (horaSeleccionada != null) {
            for (JButton btn : slots) {
                if (btn.getText().equals(horaSeleccionada)) {
                    btn.setBackground(SLOT_SEL);
                    btn.setForeground(new Color(15, 15, 15));
                    btn.setBorder(BorderFactory.createLineBorder(SLOT_SEL, 2));
                    break;
                }
            }
        }
        gridSlots.revalidate();
        gridSlots.repaint();
    }

    private void seleccionarHora(String hora, JButton seleccionado) {
        for (JButton btn : slots) {
            if (btn.isEnabled()) {
                btn.setBackground(SLOT_NORMAL);
                btn.setForeground(TEXTO);
                btn.setBorder(BorderFactory.createLineBorder(BORDE));
            }
        }
        seleccionado.setBackground(SLOT_SEL);
        seleccionado.setForeground(new Color(15, 15, 15));
        seleccionado.setBorder(BorderFactory.createLineBorder(SLOT_SEL, 2));
        horaSeleccionada = hora;
        actualizarResumen();
    }

    private void actualizarSlots() {
        if (citaEnProceso == null || citaEnProceso.getBarberia() == null) return;

        String fecha      = new java.text.SimpleDateFormat("yyyy/MM/dd")
                .format(java.sql.Date.valueOf(fechaSeleccionada));
        String barberiaId = citaEnProceso.getBarberia().getId();

        List<HorarioDTO> horarios = facadeHorario.obtenerHorariosPorBarberia(barberiaId);

        String diaSemana = java.text.Normalizer
                .normalize(fechaSeleccionada.getDayOfWeek()
                        .getDisplayName(java.time.format.TextStyle.FULL,
                                new java.util.Locale("es", "MX"))
                        .toUpperCase(), java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        HorarioDTO horarioDelDia = null;
        for (HorarioDTO h : horarios) {
            if (diaSemana.equals(h.getDiaSemana())) {
                horarioDelDia = h;
                break;
            }
        }

        if (horarioDelDia == null) {
            slots.clear();
            gridSlots.removeAll();
            JLabel lbl = new JLabel("Esta barberia no abre ese dia.");
            lbl.setFont(new Font("Comic Sans MS", Font.ITALIC, 13));
            lbl.setForeground(TEXTO_MUTED);
            gridSlots.add(lbl);
            gridSlots.revalidate();
            gridSlots.repaint();
            horaSeleccionada = null;
            lblResumen.setText("Selecciona una fecha y un horario para continuar");
            return;
        }

        java.time.LocalTime apertura = java.time.LocalTime.parse(horarioDelDia.getHoraApertura());
        java.time.LocalTime cierre   = java.time.LocalTime.parse(horarioDelDia.getHoraCierre());
        generarSlots(apertura, cierre);

        List<String> ocupadas = facadeCita.obtenerHorasOcupadas(barberiaId, fecha);
        // Deshabilitar slots ocupados
        for (JButton btn : slots) {
            String horaBtn = btn.getText();
            String horaComp;
            if (horaBtn.startsWith("0")) {
                horaComp = horaBtn.substring(1);
            } else {
                horaComp = horaBtn;
            }
            if (ocupadas.contains(horaComp)) {
                btn.setEnabled(false);
                btn.setBackground(new Color(15, 15, 15));
                btn.setForeground(new Color(45, 55, 80));
                btn.setBorder(BorderFactory.createLineBorder(new Color(28, 28, 28)));
                btn.setToolTipText("Horario no disponible");
            }
        }

        // Ocultar horas pasadas si es hoy
        if (fechaSeleccionada.equals(LocalDate.now())) {
            java.time.LocalTime ahora = java.time.LocalTime.now();
            for (JButton btn : slots) {
                String textoHora = btn.getText();
                String textoNormalizado;
                if (textoHora.length() == 4) {
                    textoNormalizado = "0" + textoHora;
                } else {
                    textoNormalizado = textoHora;
                }
                java.time.LocalTime horaBtn = java.time.LocalTime.parse(textoNormalizado);
                if (horaBtn.isBefore(ahora)) {
                    btn.setVisible(false);
                }
            }

            // Verificar si quedaron slots disponibles después de ocultar horas pasadas
            boolean hayDisponibles = false;
            for (JButton btn : slots) {
                if (btn.isVisible() && btn.isEnabled()) {
                    hayDisponibles = true;
                    break;
                }
            }
            if (!hayDisponibles) {
                slots.clear();
                gridSlots.removeAll();
                JLabel lbl = new JLabel("No hay horarios disponibles para hoy.");
                lbl.setFont(new Font("Comic Sans MS", Font.ITALIC, 13));
                lbl.setForeground(TEXTO_MUTED);
                gridSlots.add(lbl);
                gridSlots.revalidate();
                gridSlots.repaint();
            }
        }

        // Si la hora seleccionada quedó ocupada, limpiarla
        if (horaSeleccionada != null) {
            String horaComp;
            if (horaSeleccionada.startsWith("0")) {
                horaComp = horaSeleccionada.substring(1);
            } else {
                horaComp = horaSeleccionada;
            }
            if (ocupadas.contains(horaComp)) {
                horaSeleccionada = null;
                lblResumen.setText("Selecciona una fecha y un horario para continuar");
                lblResumen.setForeground(TEXTO_MUTED);
            }
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private String buildFechaHora() {
        String horaFmt;
        if (horaSeleccionada.startsWith("0")) {
            horaFmt = horaSeleccionada.substring(1);
        } else {
            horaFmt = horaSeleccionada;
        }
        return new java.text.SimpleDateFormat("yyyy/MM/dd").format(
                java.sql.Date.valueOf(fechaSeleccionada)) + " " + horaFmt;
    }

    private void actualizarResumen() {
        if (horaSeleccionada == null) {
            lblResumen.setText("Selecciona una fecha y un horario para continuar");
            lblResumen.setForeground(TEXTO_MUTED);
            return;
        }
        lblResumen.setText("  Fecha y hora seleccionada:  " + buildFechaHora());
        lblResumen.setForeground(TEXTO);
    }

    private void confirmarCita() {
        if (citaEnProceso == null) return;
        if (fechaSeleccionada == null || horaSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor selecciona una fecha y un horario para continuar.",
                    "Seleccion incompleta", JOptionPane.WARNING_MESSAGE);
            return;
        }
        citaEnProceso.setFechaHora(buildFechaHora());
        control.<PanelConfirmarCita>getPanel(ControlVistas.pantallaConfirmarCita).setCitaEnProceso(citaEnProceso);
        control.mostrar(ControlVistas.pantallaConfirmarCita);
    }

    public void refrescarSlots() {
        horaSeleccionada = null;
        actualizarSlots();
        lblResumen.setText("Selecciona una fecha y un horario para continuar");
        lblResumen.setForeground(TEXTO_MUTED);
    }

    public void setCitaEnProceso(CitaDTO cita) {
        this.citaEnProceso     = cita;
        this.horaSeleccionada  = null;
        this.fechaAnterior     = null;
        this.fechaSeleccionada = LocalDate.now();
        this.mesActual         = YearMonth.now();

        for (JButton b : slots) {
            b.setBackground(SLOT_NORMAL);
            b.setForeground(TEXTO);
            b.setBorder(BorderFactory.createLineBorder(BORDE));
        }
        lblResumen.setText("Selecciona una fecha y un horario para continuar");
        lblResumen.setForeground(TEXTO_MUTED);
        actualizarCalendario();
        actualizarSlots();
    }

    private JButton botonHeader(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(CARD);
        b.setForeground(TEXTO);
        b.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JButton botonNav(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(CARD);
        b.setForeground(TEXTO);
        b.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        b.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}