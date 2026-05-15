/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dto.BarberiaDTO;
import dto.ClienteDTO;
import java.awt.*;
import javax.swing.*;
import presentacion.controles.ControlVistas;
import itson.negocios_gestorbarberias.fachada.BarberiasFacade;
import itson.negocios_gestorbarberias.fachada.IBarberiasFacade;
import presentacion.utilerias.GestorSesion;
import presentacion.PanelGestionarCitas;
import presentacion.PanelSeleccionServicio;
import presentacion.PanelResenas;

/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class PanelMenuAdmin extends JPanel {

    private static final Color FONDO = new Color(10, 10, 10);
    private static final Color CARD = new Color(22, 22, 22);
    private static final Color BORDE = new Color(55, 55, 55);
    private static final Color TEXTO = new Color(241, 245, 249);
    private static final Color TEXTO_MUTED = new Color(148, 163, 184);

    private ClienteDTO admin;
    private final ControlVistas control;
    private final IBarberiasFacade facadeBarberia;

    public PanelMenuAdmin(ControlVistas control) {
        this.control = control;
        this.facadeBarberia = new BarberiasFacade();
        initUI();
    }

    public void actualizarAdmin(ClienteDTO admin) {
        this.admin = admin;
        removeAll();
        initUI();
        revalidate();
        repaint();
    }

    private void initUI() {
        setBackground(FONDO);
        setLayout(new BorderLayout());
        add(crearTopBar(), BorderLayout.NORTH);

        if (admin == null || admin.getId() == null) {
            add(crearVistaSinBarberia(), BorderLayout.CENTER);
            return;
        }

        BarberiaDTO barberia = facadeBarberia.obtenerPorBarbero(admin.getId());

        if (barberia == null) {
            add(crearVistaSinBarberia(), BorderLayout.CENTER);
        } else {
            add(crearVistaConBarberia(barberia), BorderLayout.CENTER);
        }
    }

    private JPanel crearTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(15, 15, 15));
        topBar.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        JLabel lblTitulo = new JLabel("PANEL DE ADMINISTRACION");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lblTitulo.setForeground(TEXTO);

        JPanel der = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        der.setBackground(new Color(15, 15, 15));

        JLabel lblNombre = new JLabel("Bienvenido, " + (admin != null ? admin.getNombre() : ""));
        lblNombre.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        lblNombre.setForeground(TEXTO_MUTED);

        JButton btnCerrar = boton("Cerrar Sesion");
        btnCerrar.addActionListener(e -> {
            GestorSesion.cerrarSesion();
            control.mostrar(ControlVistas.pantallaLogin);
        });

        JLabel lblLogo = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        lblLogo.setForeground(TEXTO_MUTED);

        der.add(lblNombre);
        der.add(btnCerrar);
        der.add(lblLogo);

        topBar.add(lblTitulo, BorderLayout.WEST);
        topBar.add(der, BorderLayout.EAST);
        return topBar;
    }

    private JPanel crearVistaSinBarberia() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(FONDO);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(FONDO);

        JLabel lblIcono = new JLabel("✂", SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblMensaje = new JLabel("Aún no tienes una barbería registrada");
        lblMensaje.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        lblMensaje.setForeground(TEXTO);
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Registra tu barbería para comenzar a gestionar tus citas");
        lblSub.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        lblSub.setForeground(TEXTO_MUTED);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnRegistrar = botonDorado("Registrar mi barbería");
        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegistrar.addActionListener(e -> control.mostrar(ControlVistas.pantallaRegBarberia));

        contenido.add(lblIcono);
        contenido.add(Box.createVerticalStrut(16));
        contenido.add(lblMensaje);
        contenido.add(Box.createVerticalStrut(8));
        contenido.add(lblSub);
        contenido.add(Box.createVerticalStrut(24));
        contenido.add(btnRegistrar);

        panel.add(contenido);
        return panel;
    }

    private JPanel crearVistaConBarberia(BarberiaDTO barberia) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(FONDO);

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setBackground(FONDO);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(12, 12, 12, 12);
        g.fill = GridBagConstraints.BOTH;

        JButton btnCitas = tarjetaMenu("Gestionar Citas", "Ver y administrar citas de tu barbería", "📋");
        JButton btnAgendar = tarjetaMenu("Agendar Cita", "Registrar una nueva cita para un cliente", "📅");
        JButton btnHorarios = tarjetaMenu("Gestionar Horarios", "Editar los horarios de atención", "🕐");
        JButton btnServicios = tarjetaMenu("Gestionar Servicios", "Agregar o editar tus servicios", "✂");
        JButton btnResenas = tarjetaMenu("Ver Reseñas", "Opiniones de tus clientes", "⭐");

        btnCitas.addActionListener(e -> {
            try {
                String idBarberia = facadeBarberia
                        .obtenerPorBarbero(GestorSesion.getClienteActivo().getId())
                        .getId();
                control.<PanelGestionarCitas>getPanel(ControlVistas.pantallaGestionarCitas)
                        .cargarCitasPorBarberia(idBarberia);
            } catch (Exception ex) {
            }
            control.mostrar(ControlVistas.pantallaGestionarCitas);
        });

        btnAgendar.addActionListener(e -> {
            try {
                String idBarberia = facadeBarberia
                        .obtenerPorBarbero(GestorSesion.getClienteActivo().getId())
                        .getId();
                control.<PanelSeleccionServicio>getPanel(ControlVistas.pantallaServicios)
                        .cargarServiciosPorId(idBarberia);
                control.<PanelSeleccionServicio>getPanel(ControlVistas.pantallaServicios)
                        .setPantallaOrigen(ControlVistas.pantallaMenuAdmin);
            } catch (Exception ex) {
            }
            control.mostrar(ControlVistas.pantallaServicios);
        });

        btnHorarios.addActionListener(e -> {
            control.<PanelAsignarHorario>getPanel(ControlVistas.pantallaAsignarHorario)
                    .setPantallaOrigen(ControlVistas.pantallaMenuAdmin);
            control.mostrar(ControlVistas.pantallaAsignarHorario);
        });

        btnServicios.addActionListener(e -> control.mostrar(ControlVistas.pantallaGestionarServicios));
        btnResenas.addActionListener(e -> {
            control.<PanelResenas>getPanel(ControlVistas.pantallaResenas)
                    .setPantallaOrigen(ControlVistas.pantallaMenuAdmin);
            control.mostrar(ControlVistas.pantallaResenas);
        });

        g.gridx = 0;
        g.gridy = 0;
        grid.add(btnCitas, g);
        g.gridx = 1;
        g.gridy = 0;
        grid.add(btnAgendar, g);
        g.gridx = 2;
        g.gridy = 0;
        grid.add(btnHorarios, g);
        g.gridx = 0;
        g.gridy = 1;
        grid.add(btnServicios, g);
        g.gridx = 1;
        g.gridy = 1;
        g.gridwidth = 2;
        grid.add(btnResenas, g);

        panel.add(grid);
        return panel;
    }

    private JButton tarjetaMenu(String titulo, String desc, String icono) {
        JButton btn = new JButton();
        btn.setLayout(new BoxLayout(btn, BoxLayout.Y_AXIS));
        btn.setBackground(CARD);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(BORDE, 1));
        btn.setPreferredSize(new Dimension(200, 160));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lblIcono = new JLabel(icono, SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblIcono.setBorder(BorderFactory.createEmptyBorder(16, 0, 8, 0));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        lblTitulo.setForeground(TEXTO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDesc = new JLabel("<html><center>" + desc + "</center></html>", SwingConstants.CENTER);
        lblDesc.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
        lblDesc.setForeground(TEXTO_MUTED);
        lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDesc.setBorder(BorderFactory.createEmptyBorder(4, 12, 0, 12));

        btn.add(lblIcono);
        btn.add(lblTitulo);
        btn.add(lblDesc);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(42, 42, 42));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(CARD);
            }
        });
        return btn;
    }

    private JButton boton(String texto) {
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

    private JButton botonDorado(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(new Color(212, 160, 23));
        b.setForeground(new Color(15, 15, 15));
        b.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        b.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}
