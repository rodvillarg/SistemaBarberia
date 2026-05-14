/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dto.CitaDTO;
import dto.ClienteDTO;
import dto.enums.EstadoCita;
import static dto.enums.EstadoCita.CANCELADA;
import static dto.enums.EstadoCita.COMPLETADA;
import static dto.enums.EstadoCita.CONFIRMADA;
import exceptions.ClienteNoEncontradoException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import presentacion.mediadores.CitaMediator;
import presentacion.mediadores.ICitaMediator;
import presentacion.controles.ControlVistas;
import presentacion.utilerias.GestorSesion;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class PanelGestionarMisCitas extends JPanel{
    private static final Color FONDO       = new Color(10, 10, 10);
    private static final Color CARD        = new Color(22, 22, 22);
    private static final Color BORDE       = new Color(55, 55, 55);
    private static final Color TEXTO       = new Color(241, 245, 249);
    private static final Color TEXTO_MUTED = new Color(148, 163, 184);
    private static final Color BTN_ORO     = new Color(212, 160, 23);
    private static final Color VERDE       = new Color(34, 197, 94);
    private static final Color ROJO        = new Color(239, 68, 68);
    private static final Color AMARILLO    = new Color(234, 179, 8);

    private JPanel panelLista;
    
    private final ICitaMediator mediadorCita = new CitaMediator();
    
    private final ControlVistas control;

    public PanelGestionarMisCitas(ControlVistas control) {
        this.control = control;
        initUI();
    }

    private void initUI() {
        setBackground(FONDO);
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(15, 15, 15));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblTitulo = new JLabel("GESTIONAR MIS CITAS");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lblTitulo.setForeground(TEXTO);

        JLabel lblLogo = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        lblLogo.setForeground(TEXTO_MUTED);

        topBar.add(lblTitulo, BorderLayout.WEST);
        topBar.add(lblLogo,   BorderLayout.EAST);

        panelLista = new JPanel();
        panelLista.setBackground(FONDO);
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JScrollPane scroll = new JScrollPane(panelLista,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getViewport().setBackground(FONDO);
        scroll.setOpaque(true);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(15, 15, 15));
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDE),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        JButton btnVolver = new JButton("Regresar");
        btnVolver.setBackground(CARD);
        btnVolver.setForeground(TEXTO);
        btnVolver.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        btnVolver.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setOpaque(true);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e ->
                control.mostrar(ControlVistas.pantallaMisCitas));

        footer.add(btnVolver, BorderLayout.WEST);

        add(topBar,  BorderLayout.NORTH);
        add(scroll,  BorderLayout.CENTER);
        add(footer,  BorderLayout.SOUTH);
    }

    public void cargarCitas() {
        panelLista.removeAll();
        if (!GestorSesion.haySesion()) return;

        ClienteDTO sesion = GestorSesion.getClienteActivo();
        try {
            List<CitaDTO> citas = mediadorCita
                        .obtenerCitasPorCliente(sesion.getId());

            if (citas.isEmpty()) {
                JLabel lbl = new JLabel("No tienes citas agendadas aún.");
                lbl.setFont(new Font("Comic Sans MS", Font.ITALIC, 14));
                lbl.setForeground(TEXTO_MUTED);
                panelLista.add(lbl);
            } else {
                java.time.format.DateTimeFormatter fmt =
                        java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd H:mm");
                citas.sort((a, b) -> {
                    try {
                        java.time.LocalDateTime fa = java.time.LocalDateTime.parse(a.getFechaHora(), fmt);
                        java.time.LocalDateTime fb = java.time.LocalDateTime.parse(b.getFechaHora(), fmt);
                        return fb.compareTo(fa);
                    } catch (Exception e) { return 0; }
                });
                for (CitaDTO c : citas) {
                    panelLista.add(crearCard(c));
                    panelLista.add(Box.createVerticalStrut(10));
                }
            }
        } catch (ClienteNoEncontradoException ex) {
            JLabel lbl = new JLabel("Error al cargar citas: " + ex.getMessage());
            lbl.setForeground(ROJO);
            panelLista.add(lbl);
        }
        panelLista.revalidate();
        panelLista.repaint();
    }

    private JPanel crearCard(CitaDTO cita) {
        presentacion.utilerias.PanelRedondeado card =
                new presentacion.utilerias.PanelRedondeado(10, CARD, BORDE);
        card.setLayout(new BorderLayout(12, 0));
        card.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 85));

        String fechaHora = "";
        if (cita.getFechaHora() != null) {
            fechaHora = cita.getFechaHora();
        }
        String[] partes  = fechaHora.split(" ");
        String hora = "";
        if (partes.length > 1) {
            hora = partes[1];
        }
        String fecha = "";
        try {
            java.time.LocalDate ld = java.time.LocalDate.parse(
                    partes[0], java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String dia = ld.getDayOfWeek().getDisplayName(
                    java.time.format.TextStyle.SHORT, new java.util.Locale("es", "MX"));
            String mes = ld.getMonth().getDisplayName(
                    java.time.format.TextStyle.SHORT, new java.util.Locale("es", "MX"));
            fecha = dia + " " + ld.getDayOfMonth() + " " + mes;
        } catch (Exception e) {
            if (partes.length > 0) {
                fecha = partes[0];
            }
        }
        
        Color colorEstado;
        switch (cita.getEstado()) {
            case COMPLETADA:
                colorEstado = new Color(99, 179, 237);
                break;  
            case CONFIRMADA:
                colorEstado = VERDE;
                break;
            case CANCELADA:
                colorEstado = ROJO;
                break;
            default:
                colorEstado = AMARILLO;
                break;
        }

        JPanel bloqFecha = new JPanel(new GridLayout(2, 1, 0, 2));
        bloqFecha.setOpaque(false);
        bloqFecha.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, colorEstado),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)));
        bloqFecha.setPreferredSize(new Dimension(95, 60));

        JLabel lblFecha = new JLabel(fecha, SwingConstants.LEFT);
        lblFecha.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
        lblFecha.setForeground(TEXTO_MUTED);
        JLabel lblHora = new JLabel(hora, SwingConstants.LEFT);
        lblHora.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lblHora.setForeground(TEXTO);
        bloqFecha.add(lblFecha);
        bloqFecha.add(lblHora);

        JPanel info = new JPanel(new GridLayout(2, 1, 0, 3));
        info.setBackground(CARD);
        JLabel lblBarberia = new JLabel(cita.getBarberia().getNombre());
        lblBarberia.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        lblBarberia.setForeground(TEXTO);
        JLabel lblServicio = new JLabel(cita.getServicio().getNombre()
                + "   |   " + cita.getBarberia().getDireccion());
        lblServicio.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        lblServicio.setForeground(TEXTO_MUTED);
        info.add(lblBarberia);
        info.add(lblServicio);

        JPanel der = new JPanel(new GridLayout(2, 1, 0, 4));
        der.setBackground(CARD);

        JLabel lblEstado = new JLabel(cita.getEstado().name(), SwingConstants.RIGHT);
        lblEstado.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
        lblEstado.setForeground(colorEstado);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(40, 10, 10));
        btnCancelar.setForeground(ROJO);
        btnCancelar.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
        btnCancelar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROJO),
                BorderFactory.createEmptyBorder(3, 8, 3, 8)));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setOpaque(true);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Solo iniciar botón si la cita esta confirmada
        btnCancelar.setVisible(cita.getEstado() == EstadoCita.CONFIRMADA);

        btnCancelar.addActionListener(e -> {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro que deseas cancelar esta cita?",
                "Cancelar cita", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                mediadorCita.cancelarCita(cita.getId());
                control.<PanelSeleccionFechaHora>getPanel(ControlVistas.pantallaFechaHora).refrescarSlots();
                cargarCitas();
            } catch (exceptions.CitaYaCanceladaException ex) {
                JOptionPane.showMessageDialog(this,
                        "Esta cita ya fue cancelada anteriormente.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                cargarCitas();
        }
    }
});

        der.add(lblEstado);
        der.add(btnCancelar);

        card.add(bloqFecha, BorderLayout.WEST);
        card.add(info,      BorderLayout.CENTER);
        card.add(der,       BorderLayout.EAST);
        return card;
    }

}
