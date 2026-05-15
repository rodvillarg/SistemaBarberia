/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dto.BarberiaDTO;
import dto.CitaDTO;
import dto.ServicioDTO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.SwingConstants;
import presentacion.mediadores.IServicioMediator;
import presentacion.mediadores.ServicioMediator;
import presentacion.controles.ControlVistas;
import presentacion.mediadores.BarberiaMediator;
import presentacion.mediadores.IBarberiaMediator;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class PanelSeleccionServicio extends JPanel{
    
    private static final Color FONDO       = new Color(10, 10, 10);
    private static final Color CARD        = new Color(22, 22, 22);
    private static final Color BORDE       = new Color(55, 55, 55);
    private static final Color TEXTO       = new Color(241, 245, 249);
    private static final Color TEXTO_MUTED = new Color(148, 163, 184);
    private static final Color BTN_ORO     = new Color(212, 160, 23);
    private static final Color HOVER       = new Color(42, 42, 42);
    
    private final IServicioMediator mediadorServicio = new ServicioMediator();
    private final IBarberiaMediator mediadorBarberia = new BarberiaMediator();

    private JPanel panelLista;
    private BarberiaDTO barberiaActual;
    private String pantallaOrigen = ControlVistas.pantallaInfoBarberia;
    private JScrollPane scroll;
    private final ControlVistas control;

    public PanelSeleccionServicio(ControlVistas control) {
        this.control = control;
        initUI();
    }
    
    public void setPantallaOrigen(String pantallaOrigen) {
        this.pantallaOrigen = pantallaOrigen;
    }

    private void initUI() {
        setBackground(FONDO);
        setLayout(new BorderLayout());

        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(15, 15, 15));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JLabel lblTitulo = new JLabel("SELECCIONA TU SERVICIO");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lblTitulo.setForeground(TEXTO);
        JLabel lblLogo = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        lblLogo.setForeground(TEXTO_MUTED);
        topBar.add(lblTitulo, BorderLayout.WEST);
        topBar.add(lblLogo, BorderLayout.EAST);

        // Lista de servicios — sin JScrollPane horizontal visible
        panelLista = new JPanel();
        panelLista.setBackground(FONDO);
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        scroll = new JScrollPane(panelLista,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBackground(FONDO);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(FONDO);
        scroll.getViewport().setOpaque(true);
        scroll.setOpaque(true);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getVerticalScrollBar().setBackground(new Color(10, 10, 10));
        scroll.getVerticalScrollBar().setForeground(new Color(55, 55, 55));

        // Footer con boton Regresar en esquina inferior izquierda
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(15, 15, 15));
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(55, 55, 55)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        JButton btnVolver = botonHeader("Regresar");
        btnVolver.addActionListener(e -> control.mostrar(pantallaOrigen));
        
        footer.add(btnVolver, BorderLayout.WEST);

        add(topBar, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }

    public void cargarServicios(BarberiaDTO barberia) {
        this.barberiaActual = barberia;

        panelLista.removeAll();
        List<ServicioDTO> servicios = mediadorServicio
                        .obtenerServiciosPorBarberia(barberia.getId());
        if (servicios.isEmpty()) {
            JLabel lbl = new JLabel("Esta barberia no tiene servicios registrados.");
            lbl.setFont(new Font("Comic Sans MS", Font.ITALIC, 13));
            lbl.setForeground(TEXTO_MUTED);
            panelLista.add(lbl);
        } else {
            for (ServicioDTO s : servicios) {
                panelLista.add(crearFila(s));
                panelLista.add(Box.createVerticalStrut(8));
            }
        }
        panelLista.revalidate();
        panelLista.repaint();
        if (scroll != null) {
            scroll.revalidate();
            scroll.repaint();
        }
    }
    
    public void cargarServiciosPorId(String idBarberia) {
        try {
            BarberiaDTO barberia = mediadorBarberia.obtenerPorId(idBarberia);
            cargarServicios(barberia);
        } catch (exceptions.BarberiaNoEncontradaException ex) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró la barbería.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel crearFila(ServicioDTO servicio) {
        presentacion.utilerias.PanelRedondeado fila = new presentacion.utilerias.PanelRedondeado(10, CARD, BORDE);
        fila.setLayout(new BorderLayout(15, 0));
        fila.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        fila.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel izq = new JPanel(new GridLayout(2, 1, 0, 4));
        izq.setBackground(CARD);
        JLabel lblNombre = new JLabel("<html>" + servicio.getNombre()
                + "  <font color='#d4a017'>" + servicio.getDuracionMinutos() + " min</font></html>");
        lblNombre.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        lblNombre.setForeground(TEXTO);
        JLabel lblDesc = new JLabel(servicio.getDescripcion());
        lblDesc.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        lblDesc.setForeground(TEXTO_MUTED);
        izq.add(lblNombre);
        izq.add(lblDesc);

        JLabel lblPrecio = new JLabel("$" + String.format("%.0f", servicio.getPrecio()),
                SwingConstants.RIGHT);
        lblPrecio.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        lblPrecio.setForeground(BTN_ORO);
        lblPrecio.setPreferredSize(new Dimension(80, 44));

        fila.add(izq,       BorderLayout.CENTER);
        fila.add(lblPrecio, BorderLayout.EAST);

        MouseAdapter ma = new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { seleccionar(servicio); }
            @Override public void mouseEntered(MouseEvent e) {
                fila.setBackground(HOVER); izq.setBackground(HOVER);
            }
            @Override public void mouseExited(MouseEvent e) {
                fila.setBackground(CARD); izq.setBackground(CARD);
            }
        };
        fila.addMouseListener(ma);
        return fila;
    }

    private void seleccionar(ServicioDTO servicio) {
        CitaDTO cita = new CitaDTO();
        cita.setBarberia(barberiaActual);
        cita.setServicio(servicio);
        cita.setCliente(presentacion.utilerias.GestorSesion.getClienteActivo());
        control.<PanelSeleccionFechaHora>getPanel(ControlVistas.pantallaFechaHora).setCitaEnProceso(cita);
        control.mostrar(ControlVistas.pantallaFechaHora);
    }

    private JButton botonHeader(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(CARD); b.setForeground(TEXTO);
        b.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)));
        b.setFocusPainted(false); b.setOpaque(true);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

}
