/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dto.ClienteDTO;
import dto.enums.RolUsuario;
import exceptions.UsuarioDuplicadoException;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.*;
import javax.swing.*;
import presentacion.controles.ControlVistas;
import presentacion.utilerias.GestorSesion;
import presentacion.mediadores.ClienteMediator;
import presentacion.mediadores.IClienteMediator;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class PanelRegistroCuenta extends JPanel {
    
    private final IClienteMediator mediadorCliente = new ClienteMediator();
    
    private static final Color FONDO       = new Color(10, 10, 10);
    private static final Color CARD        = new Color(22, 22, 22);
    private static final Color BORDE       = new Color(55, 55, 55);
    private static final Color TEXTO       = new Color(241, 245, 249);
    private static final Color TEXTO_MUTED = new Color(148, 163, 184);
    private static final Color BTN_ORO     = new Color(212, 160, 23);
    private static final Font  FONT_LABEL  = new Font("Comic Sans MS", Font.BOLD, 13);

    private JTextField     txtUsuario;
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmar;
    private JRadioButton   rbCliente;
    private JRadioButton   rbBarbero;
    private ClienteDTO     datosPaso1;

    private final ControlVistas control;

    public PanelRegistroCuenta(ControlVistas control) {
        this.control = control;
        initUI();
    }

    private void initUI() {
        setBackground(FONDO);
        setLayout(new GridBagLayout());

        JPanel card = new presentacion.utilerias.PanelRedondeado(16, CARD, BORDE);
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(480, 460));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CARD);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
        JLabel lblTitulo = new JLabel("Crear Cuenta");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        lblTitulo.setForeground(TEXTO);
        JLabel lblLogo = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        lblLogo.setForeground(TEXTO_MUTED);
        JLabel lblPaso = new JLabel("Paso 2 de 2  —  Datos de acceso");
        lblPaso.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        lblPaso.setForeground(TEXTO_MUTED);
        JPanel headerTop = new JPanel(new BorderLayout());
        headerTop.setBackground(CARD);
        headerTop.add(lblTitulo, BorderLayout.WEST);
        headerTop.add(lblLogo, BorderLayout.EAST);
        header.add(headerTop, BorderLayout.NORTH);
        header.add(lblPaso, BorderLayout.SOUTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(CARD);
        form.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;

        txtUsuario    = campo();
        txtContrasena = new JPasswordField();
        estilizar(txtContrasena);
        txtConfirmar  = new JPasswordField();
        estilizar(txtConfirmar);

        // Usuario
        g.gridy = 0; g.insets = new Insets(6, 0, 1, 0);
        JLabel lblUs = new JLabel("Usuario"); lblUs.setFont(FONT_LABEL); lblUs.setForeground(TEXTO);
        form.add(lblUs, g);
        g.gridy = 1; g.insets = new Insets(1, 0, 6, 0);
        form.add(txtUsuario, g);

        // Contraseña
        g.gridy = 2; g.insets = new Insets(6, 0, 1, 0);
        JLabel lblPw = new JLabel("Contrasena"); lblPw.setFont(FONT_LABEL); lblPw.setForeground(TEXTO);
        form.add(lblPw, g);
        g.gridy = 3; g.insets = new Insets(1, 0, 6, 0);
        form.add(txtContrasena, g);

        // Confirmar contraseña
        g.gridy = 4; g.insets = new Insets(6, 0, 1, 0);
        JLabel lblCf = new JLabel("Confirmar contrasena"); lblCf.setFont(FONT_LABEL); lblCf.setForeground(TEXTO);
        form.add(lblCf, g);
        g.gridy = 5; g.insets = new Insets(1, 0, 6, 0);
        form.add(txtConfirmar, g);

        // Rol
        g.gridy = 6; g.insets = new Insets(8, 0, 4, 0);
        JLabel lblRol = new JLabel("Tipo de cuenta"); lblRol.setFont(FONT_LABEL); lblRol.setForeground(TEXTO);
        form.add(lblRol, g);
        g.gridy = 7; g.insets = new Insets(1, 0, 6, 0);
        rbCliente = new JRadioButton("Cliente");
        rbBarbero = new JRadioButton("Barbero / Dueno de barberia");
        rbCliente.setSelected(true);
        for (JRadioButton rb : new JRadioButton[]{rbCliente, rbBarbero}) {
            rb.setBackground(CARD);
            rb.setForeground(TEXTO_MUTED);
            rb.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
            rb.setFocusPainted(false);
            rb.setBorderPainted(false);
        }
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbCliente); grupo.add(rbBarbero);
        JPanel panelRol = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelRol.setBackground(CARD);
        panelRol.add(rbCliente);
        panelRol.add(Box.createHorizontalStrut(20));
        panelRol.add(rbBarbero);
        form.add(panelRol, g);

        JPanel footer = new JPanel();
        footer.setBackground(new Color(15, 15, 15));
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(55, 55, 55)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));

        JButton btnRegistrar = new JButton("Crear cuenta");
        btnRegistrar.setBackground(BTN_ORO);
        btnRegistrar.setForeground(new Color(15, 15, 15));
        btnRegistrar.setOpaque(true);
        btnRegistrar.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        btnRegistrar.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRegistrar.setAlignmentX(CENTER_ALIGNMENT);
        btnRegistrar.setMaximumSize(new Dimension(430, 42));

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(CARD);
        btnVolver.setForeground(TEXTO_MUTED);
        btnVolver.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        btnVolver.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setAlignmentX(CENTER_ALIGNMENT);

        footer.add(btnRegistrar);
        footer.add(Box.createVerticalStrut(6));
        footer.add(btnVolver);

        card.add(header, BorderLayout.NORTH);
        card.add(form, BorderLayout.CENTER);
        card.add(footer, BorderLayout.SOUTH);
        add(card);
        
        txtUsuario.addActionListener(e -> txtContrasena.requestFocusInWindow());
        txtContrasena.addActionListener(e -> txtConfirmar.requestFocusInWindow());
        txtConfirmar.addActionListener(e -> registrar());
        btnRegistrar.addActionListener(e -> registrar());
        btnVolver.addActionListener(e ->
                control.mostrar(ControlVistas.pantallaLogin));

        addAncestorListener(new javax.swing.event.AncestorListener() {
            @Override public void ancestorAdded(javax.swing.event.AncestorEvent e)   { limpiar(); }
            @Override public void ancestorRemoved(javax.swing.event.AncestorEvent e) {}
            @Override public void ancestorMoved(javax.swing.event.AncestorEvent e)   {}
        });
    }

    public void setDatosPaso1(ClienteDTO datos) {
        this.datosPaso1 = datos;
    }

    private void registrar() {
        if (datosPaso1 == null) {
            control.mostrar(ControlVistas.pantallaRegistro);
            return;
        }
        String usuario    = txtUsuario.getText().trim();
        char[] contrasena = txtContrasena.getPassword();
        char[] confirmar  = txtConfirmar.getPassword();

        if (usuario.isEmpty() || contrasena.length == 0) {
            JOptionPane.showMessageDialog(this,
                    "Usuario y contrasena son obligatorios.",
                    "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (usuario.length() < 3) {
            JOptionPane.showMessageDialog(this,
                    "El usuario debe tener al menos 3 caracteres.",
                    "Usuario muy corto", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (contrasena.length < 4) {
            JOptionPane.showMessageDialog(this,
                    "La contrasena debe tener al menos 4 caracteres.",
                    "Contrasena muy corta", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!java.util.Arrays.equals(contrasena, confirmar)) {
            JOptionPane.showMessageDialog(this,
                    "Las contrasenas no coinciden.",
                    "Error de confirmacion", JOptionPane.WARNING_MESSAGE);
            java.util.Arrays.fill(contrasena, '\0');
            java.util.Arrays.fill(confirmar, '\0');
            return;
        }
        RolUsuario rol = rbBarbero.isSelected() ? RolUsuario.BARBERO : RolUsuario.CLIENTE;
        datosPaso1.setUsuario(usuario);
        // Convertir a String solo para pasarlo al DTO — el BO lo hashea con BCrypt
        datosPaso1.setContrasena(new String(contrasena));
        java.util.Arrays.fill(contrasena, '\0');
        datosPaso1.setRol(rol);

        try {
            ClienteDTO creado = mediadorCliente.registrar(datosPaso1);
            GestorSesion.setClienteActivo(creado);
            JOptionPane.showMessageDialog(this,
                    "Bienvenido, " + creado.getNombre() + "!",
                    "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            if (rol == RolUsuario.BARBERO) {
                control.mostrar(ControlVistas.pantallaMenuAdmin);
            } else {
                control.mostrar(ControlVistas.pantallaBarberias);
            }
        } catch (UsuarioDuplicadoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Usuario no disponible", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiar() {
        txtUsuario.setText("");
        txtContrasena.setText("");
        txtConfirmar.setText("");
        rbCliente.setSelected(true);
    }

    private JTextField campo() {
        JTextField c = new JTextField();
        estilizar(c);
        return c;
    }

    private void estilizar(JTextField c) {
        c.setBackground(CARD);
        c.setForeground(TEXTO_MUTED);
        c.setCaretColor(TEXTO);
        c.setBorder(BorderFactory.createLineBorder(BORDE));
        c.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
        c.setPreferredSize(new Dimension(Integer.MAX_VALUE, 35));
    }
}
