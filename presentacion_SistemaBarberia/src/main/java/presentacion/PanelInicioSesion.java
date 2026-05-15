package presentacion;

import dto.ClienteDTO;
import dto.SesionDTO;
import exceptions.CredencialesInvalidasException;
import exceptions.ClienteNoEncontradoException;
import java.awt.*;
import javax.swing.*;
import itson.negocios_gestorclientes.fachada.ClientesFacade;
import itson.negocios_gestorclientes.fachada.IClientesFacade;
import presentacion.controles.ControlVistas;
import presentacion.utilerias.GestorSesion;

/**
 * Panel de inicio de sesión del cliente.
 * @author Jesus Rodrigo Villegas - 261186
 *
 */
public class PanelInicioSesion extends JPanel {
    
    private final IClientesFacade facadeLogin = new ClientesFacade();

    private static final Color FONDO       = new Color(10, 10, 10);
    private static final Color CARD        = new Color(22, 22, 22);
    private static final Color BORDE       = new Color(55, 55, 55);
    private static final Color TEXTO       = new Color(241, 245, 249);
    private static final Color TEXTO_MUTED = new Color(148, 163, 184);
    private static final Color BTN_ORO    = new Color(212, 160, 23);
    private static final Font  FONT_TITULO = new Font("Comic Sans MS", Font.BOLD, 18);
    private static final Font  FONT_NORMAL = new Font("Comic Sans MS", Font.BOLD, 14);
    private static final Font  FONT_BTN    = new Font("Comic Sans MS", Font.BOLD, 16);

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnEntrar;
    private JButton btnRegistrarse;

    private final ControlVistas control;

    public PanelInicioSesion(ControlVistas control) {
        this.control = control;
        initUI();
    }

    private void initUI() {
        setBackground(FONDO);
        setLayout(new GridBagLayout());

        JPanel card = new presentacion.utilerias.PanelRedondeado(16, CARD, BORDE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(480, 340));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(8, 30, 8, 30);
        g.weightx = 1.0;

        // Logo arriba a la derecha
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(CARD);
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));
        JLabel lblSistema = new JLabel("SISTEMA BARBERIAS");
        lblSistema.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
        lblSistema.setForeground(TEXTO_MUTED);
        JLabel lblLogo = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        lblLogo.setForeground(TEXTO);
        topBar.add(lblSistema, BorderLayout.WEST);
        topBar.add(lblLogo, BorderLayout.EAST);

        g.gridy = 0; g.gridwidth = 2;
        card.add(topBar, g);

        // Usuario
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(FONT_TITULO);
        lblUsuario.setForeground(TEXTO);
        g.gridy = 1; g.insets = new Insets(10, 30, 2, 30);
        card.add(lblUsuario, g);

        txtUsuario = new JTextField();
        txtUsuario.setBackground(CARD);
        txtUsuario.setForeground(TEXTO_MUTED);
        txtUsuario.setCaretColor(TEXTO);
        txtUsuario.setBorder(BorderFactory.createLineBorder(BORDE));
        txtUsuario.setPreferredSize(new Dimension(Integer.MAX_VALUE, 35));
        txtUsuario.setFont(FONT_NORMAL);
        g.gridy = 2; g.insets = new Insets(2, 30, 8, 30);
        card.add(txtUsuario, g);

        // Contraseña
        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setFont(FONT_TITULO);
        lblContrasena.setForeground(TEXTO);
        g.gridy = 3; g.insets = new Insets(5, 30, 2, 30);
        card.add(lblContrasena, g);

        txtContrasena = new JPasswordField();
        txtContrasena.setBackground(CARD);
        txtContrasena.setForeground(TEXTO_MUTED);
        txtContrasena.setCaretColor(TEXTO);
        txtContrasena.setBorder(BorderFactory.createLineBorder(BORDE));
        txtContrasena.setPreferredSize(new Dimension(Integer.MAX_VALUE, 35));
        g.gridy = 4; g.insets = new Insets(2, 30, 12, 30);
        card.add(txtContrasena, g);

        // Botón iniciar sesión
        btnEntrar = new JButton("Iniciar Sesion");
        btnEntrar.setBackground(BTN_ORO);
        btnEntrar.setOpaque(true);
        btnEntrar.setForeground(new Color(15, 15, 15));
        btnEntrar.setFont(FONT_BTN);
        btnEntrar.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        g.gridy = 5; g.insets = new Insets(5, 30, 5, 30);
        card.add(btnEntrar, g);

        // Botón registrarse
        btnRegistrarse = new JButton("¿No tienes cuenta? Regístrate");
        btnRegistrarse.setBackground(CARD);
        btnRegistrarse.setForeground(TEXTO_MUTED);
        btnRegistrarse.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        btnRegistrarse.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnRegistrarse.setFocusPainted(false);
        btnRegistrarse.setBorderPainted(false);
        btnRegistrarse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        g.gridy = 6; g.insets = new Insets(2, 30, 15, 30);
        card.add(btnRegistrarse, g);

        add(card);
        registrarAcciones();
    }

    private void registrarAcciones() {
        btnEntrar.addActionListener(e -> iniciarSesion());
        btnRegistrarse.addActionListener(e ->
                control.mostrar(ControlVistas.pantallaRegistro));
        txtUsuario.addActionListener(e -> txtContrasena.requestFocusInWindow());
        txtContrasena.addActionListener(e -> iniciarSesion());
    }

    public void limpiar() {
        txtUsuario.setText("");
        txtContrasena.setText("");
        txtUsuario.requestFocusInWindow();
    }

    private void iniciarSesion() {
        String usuario    = txtUsuario.getText().trim();
        char[] contrasena = txtContrasena.getPassword();
        if (usuario.isEmpty() || contrasena.length == 0) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingresa usuario y contraseña.", "Campos vacíos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            ClienteDTO cliente = facadeLogin.iniciarSesion(new SesionDTO(usuario, contrasena));
            // Limpiar la contraseña de memoria en cuanto ya no se necesita
            java.util.Arrays.fill(contrasena, '\0');
            GestorSesion.setClienteActivo(cliente);
            txtUsuario.setText("");
            txtContrasena.setText("");
            if (facadeLogin.esBarbero(cliente)) {
                control.mostrar(ControlVistas.pantallaMenuAdmin);
            } else {
                control.mostrar(ControlVistas.pantallaBarberias);
            }
        } catch (ClienteNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this,
                    "El usuario no existe. Verifica e intenta de nuevo.",
                    "Usuario no encontrado", JOptionPane.WARNING_MESSAGE);
        } catch (CredencialesInvalidasException ex) {
            JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos, vuelve a intentarlo.",
                    "Credenciales incorrectas", JOptionPane.ERROR_MESSAGE);
        }
    }
}

