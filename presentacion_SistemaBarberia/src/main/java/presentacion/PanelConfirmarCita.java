/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dto.CitaDTO;
import dto.enums.MetodoPago;
import exceptions.BarberiaNoEncontradaException;
import exceptions.CitaConflictoClienteException;
import exceptions.ClienteNoEncontradoException;
import exceptions.HorarioNoDisponibleException;
import exceptions.ServicioNoEncontradoException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;
import itson.negocios_gestorcitas.fachada.CitasFacade;
import itson.negocios_gestorcitas.fachada.ICitasFacade;
import presentacion.controles.ControlVistas;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class PanelConfirmarCita extends JPanel {

    private static final Color FONDO = new Color(10, 10, 10);
    private static final Color CARD = new Color(22, 22, 22);
    private static final Color CARD_INNER = new Color(24, 24, 24);
    private static final Color BORDE = new Color(55, 55, 55);
    private static final Color TEXTO = new Color(241, 245, 249);
    private static final Color TEXTO_MUTED = new Color(148, 163, 184);
    private static final Color BTN_ORO = new Color(212, 160, 23);

    private final ICitasFacade facadeCita = new CitasFacade();

    private CitaDTO citaEnProceso;
    private JPanel panelResumen;
    private String numeroTarjetaMascarado = null;
    private JRadioButton rbEfectivo;
    private JRadioButton rbTarjeta;
    private ButtonGroup grupo;

    private final ControlVistas control;

    public PanelConfirmarCita(ControlVistas control) {
        this.control = control;
        initUI();
    }

    private void initUI() {
        setBackground(FONDO);
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(15, 15, 15));
        topBar.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        JLabel lblTitulo = new JLabel("CONFIRMAR CITA");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lblTitulo.setForeground(TEXTO);
        JLabel lblLogo = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        lblLogo.setForeground(TEXTO_MUTED);
        topBar.add(lblTitulo, BorderLayout.WEST);
        topBar.add(lblLogo, BorderLayout.EAST);

        JPanel centro = new JPanel(new GridLayout(1, 2, 15, 0));
        centro.setBackground(FONDO);
        centro.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
        centro.add(crearPanelResumen());
        centro.add(crearPanelPago());

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(15, 15, 15));
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(55, 55, 55)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        JButton btnVolver = botonSecundario("Regresar");
        btnVolver.addActionListener(e -> control.mostrar(ControlVistas.pantallaFechaHora));

        JButton btnConfirmar = new JButton("Agendar Cita");
        btnConfirmar.setBackground(BTN_ORO);
        btnConfirmar.setForeground(new Color(15, 15, 15));
        btnConfirmar.setOpaque(true);
        btnConfirmar.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        btnConfirmar.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setBorderPainted(false);
        btnConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnConfirmar.addActionListener(e -> agendarCita());

        footer.add(btnVolver, BorderLayout.WEST);
        footer.add(btnConfirmar, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }

    private JPanel crearPanelResumen() {
        presentacion.utilerias.PanelRedondeado card
                = new presentacion.utilerias.PanelRedondeado(10, CARD, BORDE);
        card.setLayout(new BorderLayout(0, 8));
        card.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));

        JLabel lblTitulo = new JLabel("RESUMEN DE TU CITA");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        lblTitulo.setForeground(TEXTO_MUTED);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

        panelResumen = new JPanel();
        panelResumen.setOpaque(false);
        panelResumen.setLayout(new BoxLayout(panelResumen, BoxLayout.Y_AXIS));

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(panelResumen, BorderLayout.CENTER);
        return card;
    }

    private JPanel crearPanelPago() {
        JPanel outer = new JPanel(new BorderLayout(0, 10));
        outer.setBackground(FONDO);

        JLabel lblTitulo = new JLabel("METODO DE PAGO");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        lblTitulo.setForeground(TEXTO_MUTED);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(2, 0, 6, 0));

        rbEfectivo = crearRadio("Efectivo");
        rbTarjeta = crearRadio("Tarjeta");

        grupo = new ButtonGroup();
        grupo.add(rbEfectivo);
        grupo.add(rbTarjeta);

        JPanel opciones = new JPanel();
        opciones.setLayout(new BoxLayout(opciones, BoxLayout.Y_AXIS));
        opciones.setOpaque(false);
        opciones.add(crearFilaPago(rbEfectivo, "Pago en efectivo al llegar"));
        opciones.add(Box.createVerticalStrut(8));
        opciones.add(crearFilaPago(rbTarjeta, "Tarjeta de credito o debito"));

        outer.add(lblTitulo, BorderLayout.NORTH);
        outer.add(opciones, BorderLayout.CENTER);
        return outer;
    }

    private JPanel crearFilaPago(JRadioButton rb, String desc) {
        presentacion.utilerias.PanelRedondeado fila
                = new presentacion.utilerias.PanelRedondeado(10, CARD, BORDE);
        fila.setLayout(new BorderLayout(0, 3));
        fila.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
        fila.setMinimumSize(new Dimension(0, 65));
        fila.setPreferredSize(new Dimension(0, 65));
        fila.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        rb.setOpaque(false);
        JLabel lblDesc = new JLabel(desc);
        lblDesc.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
        lblDesc.setForeground(TEXTO_MUTED);
        lblDesc.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        fila.add(rb, BorderLayout.NORTH);
        fila.add(lblDesc, BorderLayout.CENTER);
        fila.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                rb.setSelected(true);
            }
        });
        return fila;
    }

    private JRadioButton crearRadio(String texto) {
        JRadioButton rb = new JRadioButton(texto);
        rb.setOpaque(false);
        rb.setForeground(TEXTO);
        rb.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        rb.setFocusPainted(false);
        rb.setBorderPainted(false);
        return rb;
    }

    private void actualizarResumen() {
        panelResumen.removeAll();
        if (citaEnProceso == null) {
            return;
        }

        String fechaHora = "";
        if (citaEnProceso.getFechaHora() != null) {
            fechaHora = citaEnProceso.getFechaHora();
        }
        String[] partes = fechaHora.split(" ");
        String fecha = partes.length > 0 ? partes[0] : "";
        String hora = partes.length > 1 ? partes[1] : "";

        panelResumen.add(filaResumen("Fecha:", fecha, TEXTO, false));
        panelResumen.add(filaResumen("Hora:", hora, BTN_ORO, true));

        String nombreBarberia = citaEnProceso.getBarberia() != null ? citaEnProceso.getBarberia().getNombre() : "";
        String nombreServicio = citaEnProceso.getServicio() != null ? citaEnProceso.getServicio().getNombre() : "";
        String duracion = citaEnProceso.getServicio() != null ? citaEnProceso.getServicio().getDuracionMinutos() + " min" : "";
        String precio = citaEnProceso.getServicio() != null ? "$" + String.format("%.2f", citaEnProceso.getServicio().getPrecio()) : "";
        String direccion = citaEnProceso.getBarberia() != null ? citaEnProceso.getBarberia().getDireccion() : "";

        String[][] datos = {
            {"Barberia:", nombreBarberia},
            {"Servicio:", nombreServicio},
            {"Duracion:", duracion},
            {"Precio:", precio},
            {"Direccion:", direccion}
        };
        for (String[] f : datos) {
            panelResumen.add(filaResumen(f[0], f[1], TEXTO, false));
        }
        if (numeroTarjetaMascarado != null) {
            panelResumen.add(filaResumen("Tarjeta:", numeroTarjetaMascarado, TEXTO, false));
        }
        panelResumen.revalidate();
        panelResumen.repaint();
    }

    private JPanel filaResumen(String key, String value, Color colorVal, boolean horaGrande) {
        JPanel fila = new JPanel(new BorderLayout(8, 0));
        fila.setOpaque(false);
        fila.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDE),
                BorderFactory.createEmptyBorder(9, 0, 9, 0)));

        JLabel lbl = new JLabel(key);
        lbl.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        lbl.setForeground(TEXTO_MUTED);
        lbl.setPreferredSize(new Dimension(90, 20));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Comic Sans MS", Font.BOLD, horaGrande ? 18 : 14));
        val.setForeground(colorVal);
        val.setHorizontalAlignment(SwingConstants.RIGHT);

        fila.add(lbl, BorderLayout.WEST);
        fila.add(val, BorderLayout.CENTER);
        return fila;
    }

    private void agendarCita() {
        if (citaEnProceso == null) {
            return;
        }
        if (!rbEfectivo.isSelected() && !rbTarjeta.isSelected()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor selecciona un metodo de pago.",
                    "Metodo de pago requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        MetodoPago metodo = rbTarjeta.isSelected() ? MetodoPago.TARJETA : MetodoPago.EFECTIVO;
        citaEnProceso.setMetodoPago(metodo);

        if (metodo == MetodoPago.TARJETA) {
            control.<PanelPagoTarjeta>getPanel(ControlVistas.pantallaPagoTarjeta)
                    .cargarCita(citaEnProceso);
            control.mostrar(ControlVistas.pantallaPagoTarjeta);
            return;
        }

        try {
            CitaDTO confirmada = facadeCita.agendarCita(citaEnProceso);
            control.<PanelConfirmacion>getPanel(ControlVistas.pantallaConfirmacion)
                    .setNumeroTarjeta(null);
            control.<PanelConfirmacion>getPanel(ControlVistas.pantallaConfirmacion)
                    .mostrarCita(confirmada);
            control.mostrar(ControlVistas.pantallaConfirmacion);
        } catch (HorarioNoDisponibleException ex) {
            JOptionPane.showMessageDialog(this,
                    "El horario seleccionado ya no esta disponible.\nElige otro horario.",
                    "Horario No Disponible", JOptionPane.WARNING_MESSAGE);
            control.mostrar(ControlVistas.pantallaFechaHora);
        } catch (CitaConflictoClienteException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ya tienes una cita agendada a esa hora en otra barberia.",
                    "Conflicto de cita", JOptionPane.WARNING_MESSAGE);
            control.mostrar(ControlVistas.pantallaFechaHora);
        } catch (ClienteNoEncontradoException | BarberiaNoEncontradaException
                | ServicioNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setCitaEnProceso(CitaDTO cita) {
        this.citaEnProceso = cita;
        this.numeroTarjetaMascarado = null;
        grupo.clearSelection();
        actualizarResumen();
    }

    public void setNumeroTarjeta(String numero) {
        if (numero != null && numero.length() == 16) {
            this.numeroTarjetaMascarado = "**** **** **** " + numero.substring(12);
        }
        actualizarResumen();
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
