/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dto.ClienteDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.DocumentFilter;
import presentacion.controles.ControlVistas;

/**
 *
 * @author Jesus Rodrigo Villegas - 261186
 */
public class PanelRegistro extends JPanel{
    private static final Color FONDO       = new Color(10, 10, 10);
    private static final Color CARD        = new Color(22, 22, 22);
    private static final Color BORDE       = new Color(55, 55, 55);
    private static final Color TEXTO       = new Color(241, 245, 249);
    private static final Color TEXTO_MUTED = new Color(148, 163, 184);
    private static final Color BTN_ORO     = new Color(212, 160, 23);
    private static final Font  FONT_LABEL  = new Font("Comic Sans MS", Font.BOLD, 13);

    private JTextField txtNombre, txtApellido, txtCorreo, txtTelefono;

    private final ControlVistas control;

    public PanelRegistro(ControlVistas control) {
        this.control = control;
        initUI();
    }

    private void initUI() {
        setBackground(FONDO);
        setLayout(new GridBagLayout());

        JPanel card = new presentacion.utilerias.PanelRedondeado(16, CARD, BORDE);
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(480, 430));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CARD);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
        JLabel lblTitulo = new JLabel("Crear Cuenta");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        lblTitulo.setForeground(TEXTO);
        JLabel lblLogo = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        lblLogo.setForeground(TEXTO_MUTED);
        JLabel lblPaso = new JLabel("Paso 1 de 2  —  Datos personales");
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

        txtNombre   = campo();
        txtApellido = campo();
        txtCorreo   = campo();
        txtTelefono = campo();
        ((javax.swing.text.AbstractDocument) txtTelefono.getDocument())
                .setDocumentFilter(new SoloNumeros(10));

        String[] etiquetas = {"Nombre", "Apellido",
                              "Correo electronico", "Telefono (10 digitos)"};
        JTextField[] campos = {txtNombre, txtApellido, txtCorreo, txtTelefono};

        for (int i = 0; i < etiquetas.length; i++) {
            JLabel lbl = new JLabel(etiquetas[i]);
            lbl.setFont(FONT_LABEL);
            lbl.setForeground(TEXTO);
            g.gridy = i * 2; g.insets = new Insets(6, 0, 1, 0);
            form.add(lbl, g);
            g.gridy = i * 2 + 1; g.insets = new Insets(1, 0, 6, 0);
            form.add(campos[i], g);
        }

        JPanel footer = new JPanel();
        footer.setBackground(new Color(15, 15, 15));
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(55, 55, 55)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));

        JButton btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setBackground(BTN_ORO);
        btnSiguiente.setForeground(new Color(15, 15, 15));
        btnSiguiente.setOpaque(true);
        btnSiguiente.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        btnSiguiente.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        btnSiguiente.setFocusPainted(false);
        btnSiguiente.setBorderPainted(false);
        btnSiguiente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSiguiente.setAlignmentX(CENTER_ALIGNMENT);
        btnSiguiente.setMaximumSize(new Dimension(430, 42));

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(CARD);
        btnVolver.setForeground(TEXTO_MUTED);
        btnVolver.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        btnVolver.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setAlignmentX(CENTER_ALIGNMENT);

        footer.add(btnSiguiente);
        footer.add(Box.createVerticalStrut(6));
        footer.add(btnVolver);

        card.add(header, BorderLayout.NORTH);
        card.add(form, BorderLayout.CENTER);
        card.add(footer, BorderLayout.SOUTH);
        add(card);
        
        txtNombre.addActionListener(e -> txtApellido.requestFocusInWindow());
        txtApellido.addActionListener(e -> txtCorreo.requestFocusInWindow());
        txtCorreo.addActionListener(e -> txtTelefono.requestFocusInWindow());
        txtTelefono.addActionListener(e -> siguiente());
        btnSiguiente.addActionListener(e -> siguiente());
        btnVolver.addActionListener(e ->
                control.mostrar(ControlVistas.pantallaLogin));

        addAncestorListener(new javax.swing.event.AncestorListener() {
            @Override public void ancestorAdded(javax.swing.event.AncestorEvent e)   { limpiar(); }
            @Override public void ancestorRemoved(javax.swing.event.AncestorEvent e) {}
            @Override public void ancestorMoved(javax.swing.event.AncestorEvent e)   {}
        });
    }

    private void siguiente() {
        String nombre   = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String correo   = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nombre, apellido y correo son obligatorios.",
                    "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!correo.matches("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(this,
                    "El correo no tiene un formato valido.\nEjemplo: nombre@correo.com",
                    "Correo invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!telefono.isEmpty() && !telefono.matches("[0-9]{10}")) {
            JOptionPane.showMessageDialog(this,
                    "El telefono debe tener exactamente 10 digitos.\nEjemplo: 6621234567",
                    "Telefono invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ClienteDTO parcial = new ClienteDTO();
        parcial.setNombre(nombre);
        parcial.setApellido(apellido);
        parcial.setCorreo(correo);
        parcial.setTelefono(telefono);
        control.<PanelRegistroCuenta>getPanel(ControlVistas.pantallaRegistroCuenta).setDatosPaso1(parcial);
        control.mostrar(ControlVistas.pantallaRegistroCuenta);
    }
    
    

    private void limpiar() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
    }

    private JTextField campo() {
        JTextField c = new JTextField();
        c.setBackground(CARD);
        c.setForeground(TEXTO_MUTED);
        c.setCaretColor(TEXTO);
        c.setBorder(BorderFactory.createLineBorder(BORDE));
        c.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
        c.setPreferredSize(new Dimension(Integer.MAX_VALUE, 35));
        return c;
    }

    /**
     * Filtro que solo permite digitos numericos en el campo de texto.
     * Se aplica al campo de telefono para evitar que el usuario ingrese letras.
     */
    private static class SoloNumeros extends javax.swing.text.DocumentFilter {

        private final int limite;

        SoloNumeros(int limite) { this.limite = limite; }

        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string,
                javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
            if (string != null && string.matches("[0-9]+")
                    && fb.getDocument().getLength() + string.length() <= limite) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,
                javax.swing.text.AttributeSet attrs) throws javax.swing.text.BadLocationException {
            if (text != null && text.matches("[0-9]*")
                    && fb.getDocument().getLength() - length + text.length() <= limite) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}
