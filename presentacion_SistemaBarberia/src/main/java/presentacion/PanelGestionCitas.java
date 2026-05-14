/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dto.BarberiaDTO;
import dto.CitaDTO;
import dto.ClienteDTO;
import static dto.enums.EstadoCita.CANCELADA;
import static dto.enums.EstadoCita.CONFIRMADA;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import presentacion.mediadores.BarberiaMediator;
import presentacion.mediadores.CitaMediator;
import presentacion.mediadores.IBarberiaMediator;
import presentacion.mediadores.ICitaMediator;
import presentacion.controles.ControlVistas;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class PanelGestionCitas extends JPanel{
    private JPanel panelLista;
    private JLabel lblTituloBarberia;
    private ClienteDTO adminActual;
    
    private final IBarberiaMediator mediadorBarberia = new BarberiaMediator();
    private final ICitaMediator     mediadorCita     = new CitaMediator();

    private final ControlVistas control;

    public PanelGestionCitas(ControlVistas control) {
        this.control = control;
        initUI();
    }

    private void initUI() {
        setBackground(new Color(10, 10, 10));
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(15, 15, 15));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        lblTituloBarberia = new JLabel("GESTION DE CITAS");
        lblTituloBarberia.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lblTituloBarberia.setForeground(new Color(241, 245, 249));

        JPanel der = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        der.setBackground(new Color(15, 15, 15));
        JButton btnVolver = botonHeader("← Menu Principal");
        JLabel  lblLogo   = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        lblLogo.setForeground(new Color(148, 163, 184));
        btnVolver.addActionListener(e ->
                control.mostrar(ControlVistas.pantallaMenuAdmin));
        der.add(btnVolver);
        der.add(lblLogo);

        topBar.add(lblTituloBarberia, BorderLayout.WEST);
        topBar.add(der, BorderLayout.EAST);

        panelLista = new JPanel();
        panelLista.setBackground(new Color(10, 10, 10));
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        JScrollPane scroll = new JScrollPane(panelLista);
        scroll.getViewport().setBackground(new Color(10, 10, 10));
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(topBar, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    public void cargarCitas(ClienteDTO admin) {
        this.adminActual = admin;
        panelLista.removeAll();

        // El admin tiene idBarbero — buscamos la barberia que le corresponde
        // Por ahora mostramos todas las barberias activas y filtramos por idBarbero
        List<BarberiaDTO> barberias = mediadorBarberia.obtenerBarberiasActivas();
        BarberiaDTO misBarberia = null;
        for (BarberiaDTO b : barberias) {
            if (admin.getId() != null && admin.getId().equals(b.getIdBarbero())) {
                misBarberia = b;
                break;
            }
        }

        if (misBarberia == null) {
            // Sin barberia asignada — iniciar mensaje en lugar de todas las citas
            JLabel lbl = new JLabel("No tienes una barberia asignada todavia.");
            lbl.setFont(new Font("Comic Sans MS", Font.ITALIC, 14));
            lbl.setForeground(new Color(148, 163, 184));
            JLabel lbl2 = new JLabel("Ve al Menu Principal y registra tu barberia primero.");
            lbl2.setFont(new Font("Comic Sans MS", Font.ITALIC, 12));
            lbl2.setForeground(new Color(100, 120, 160));
            panelLista.add(lbl);
            panelLista.add(Box.createVerticalStrut(6));
            panelLista.add(lbl2);
        } else {
            lblTituloBarberia.setText("CITAS — " + misBarberia.getNombre());
            cargarCitasDeBarberia(misBarberia);
        }

        panelLista.revalidate();
        panelLista.repaint();
    }

    private void cargarCitasDeBarberia(BarberiaDTO barberia) {
        List<CitaDTO> citas = mediadorCita.obtenerCitasPorBarberia(barberia.getId());
        if (citas.isEmpty()) {
            JLabel lbl = new JLabel("No hay citas registradas para " + barberia.getNombre());
            lbl.setFont(new Font("Comic Sans MS", Font.ITALIC, 13));
            lbl.setForeground(new Color(148, 163, 184));
            panelLista.add(lbl);
            panelLista.add(Box.createVerticalStrut(8));
            return;
        }
        for (CitaDTO c : citas) {
            panelLista.add(crearCard(c));
            panelLista.add(Box.createVerticalStrut(8));
        }
    }

    private JPanel crearCard(CitaDTO cita) {
        presentacion.utilerias.PanelRedondeado card = new presentacion.utilerias.PanelRedondeado(10, new Color(22, 22, 22), new Color(55, 55, 55));
        card.setLayout(new BorderLayout(12, 0));
        card.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        String fechaHora = "";
        if (cita.getFechaHora() != null) {
            fechaHora = cita.getFechaHora();
        }
        String[] partes = fechaHora.split(" ");
        String fecha = "";
        if (partes.length > 0) {
            fecha = partes[0];
        }
        String hora = "";
        if (partes.length > 1) {
            hora = partes[1];
        }

        JPanel bloqFecha = new JPanel(new GridLayout(2, 1, 0, 2));
        bloqFecha.setOpaque(false);
        bloqFecha.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, new Color(212, 160, 23)),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)));
        bloqFecha.setPreferredSize(new Dimension(95, 60));
        JLabel lblFecha = new JLabel(fecha, SwingConstants.LEFT);
        lblFecha.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
        lblFecha.setForeground(new Color(148, 163, 184));
        JLabel lblHora = new JLabel(hora, SwingConstants.LEFT);
        lblHora.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lblHora.setForeground(new Color(241, 245, 249));
        bloqFecha.add(lblFecha);
        bloqFecha.add(lblHora);

        JPanel info = new JPanel(new GridLayout(3, 1, 0, 2));
        info.setBackground(new Color(22, 22, 22));
        String nombreCliente = "Cliente";
        if (cita.getCliente() != null) {
            nombreCliente = cita.getCliente().getNombreCompleto();
        }
        JLabel lblCliente = new JLabel(nombreCliente);
        lblCliente.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        lblCliente.setForeground(new Color(241, 245, 249));
        String nombreServicio = "";
        if (cita.getServicio() != null) {
            nombreServicio = cita.getServicio().getNombre();
        }
        JLabel lblServicio = new JLabel(nombreServicio);
        lblServicio.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        lblServicio.setForeground(new Color(148, 163, 184));
        String nombreBarberia = "";
        if (cita.getBarberia() != null) {
            nombreBarberia = "✂  " + cita.getBarberia().getNombre();
        }
        JLabel lblBarberia = new JLabel(nombreBarberia);
        lblBarberia.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
        lblBarberia.setForeground(new Color(100, 120, 160));
        info.add(lblCliente);
        info.add(lblServicio);
        info.add(lblBarberia);

        JPanel der = new JPanel(new GridLayout(2, 1, 0, 4));
        der.setBackground(new Color(22, 22, 22));
        Color colorEstado;
        switch (cita.getEstado()) {
            case CONFIRMADA:
                colorEstado = new Color(34, 197, 94);
                break;
            case CANCELADA:
                colorEstado = new Color(239, 68, 68);
                break;
            default:
                colorEstado = new Color(234, 179, 8);
                break;
        }
        JLabel lblEstado = new JLabel(cita.getEstado().name(), SwingConstants.RIGHT);
        lblEstado.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
        lblEstado.setForeground(colorEstado);
        String textoPrecio = "";
        if (cita.getServicio() != null) {
            textoPrecio = "$" + String.format("%.2f", cita.getServicio().getPrecio());
        }
        JLabel lblPrecio = new JLabel(textoPrecio, SwingConstants.RIGHT);
        lblPrecio.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        lblPrecio.setForeground(new Color(241, 245, 249));
        der.add(lblEstado);
        der.add(lblPrecio);

        card.add(bloqFecha, BorderLayout.WEST);
        card.add(info, BorderLayout.CENTER);
        card.add(der, BorderLayout.EAST);
        return card;
    }

    private JButton botonHeader(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(new Color(22, 22, 22));
        b.setForeground(new Color(241, 245, 249));
        b.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(55, 55, 55)),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

}
