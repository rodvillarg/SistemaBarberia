/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dto.BarberiaDTO;
import exceptions.UsuarioDuplicadoException;
import java.awt.*;
import javax.swing.*;
import presentacion.controles.ControlVistas;
import itson.negocios_gestorbarberias.fachada.BarberiasFacade;
import itson.negocios_gestorbarberias.fachada.IBarberiasFacade;
import presentacion.utilerias.GestorSesion;
import presentacion.utilerias.PanelRedondeado;



/**
 * @author Jesus Rodrigo Villegas Argüelles - 261186
 */
public class PanelRegistroBarberia extends JPanel {

    private static final Color FONDO = new Color(10, 10, 10);
    private static final Color CARD = new Color(22, 22, 22);
    private static final Color BORDE = new Color(55, 55, 55);
    private static final Color TEXTO = new Color(241, 245, 249);
    private static final Color TEXTO_MUTED = new Color(148, 163, 184);
    private static final Color BTN_ORO = new Color(212, 160, 23);

    private final ControlVistas control;
    private final IBarberiasFacade facadeBarberia;

    private JTextField txtNombre;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextArea txtDescripcion;

    private BarberiaDTO barberiaRegistrada;

    public PanelRegistroBarberia(ControlVistas control) {
        this.control = control;
        this.facadeBarberia = new BarberiasFacade();
        initUI();
    }

    public BarberiaDTO getBarberiaRegistrada() {
        return barberiaRegistrada;
    }

    private void initUI() {
        setBackground(FONDO);
        setLayout(new BorderLayout());
        add(crearFormulario(), BorderLayout.CENTER);
    }


    private JPanel crearFormulario() {
        JPanel contenedor = new JPanel(new GridBagLayout());
        contenedor.setBackground(FONDO);

        PanelRedondeado card = new PanelRedondeado(16, CARD, BORDE);
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(480, 480));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CARD);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JLabel lblTitulo = new JLabel("Registrar Barbería");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        lblTitulo.setForeground(TEXTO);

        JLabel lblLogo = new JLabel("BARBERIA");
        lblLogo.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        lblLogo.setForeground(TEXTO_MUTED);

        JLabel lblSub = new JLabel("Estos datos serán visibles para tus clientes");
        lblSub.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        lblSub.setForeground(TEXTO_MUTED);

        JPanel headerTop = new JPanel(new BorderLayout());
        headerTop.setBackground(CARD);
        headerTop.add(lblTitulo, BorderLayout.WEST);
        headerTop.add(lblLogo, BorderLayout.EAST);

        header.add(headerTop, BorderLayout.NORTH);
        header.add(lblSub, BorderLayout.SOUTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(CARD);
        form.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;

        txtNombre = campo();
        txtDireccion = campo();
        txtTelefono = campo();
        ((javax.swing.text.AbstractDocument) txtTelefono.getDocument())
                .setDocumentFilter(new SoloNumeros(10));

        String[] etiquetas = {"Nombre de la barbería", "Dirección", "Teléfono"};
        JTextField[] campos = {txtNombre, txtDireccion, txtTelefono};

        for (int i = 0; i < etiquetas.length; i++) {
            JLabel lbl = new JLabel(etiquetas[i]);
            lbl.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
            lbl.setForeground(TEXTO);
            g.gridy = i * 2;
            g.insets = new Insets(6, 0, 1, 0);
            form.add(lbl, g);
            g.gridy = i * 2 + 1;
            g.insets = new Insets(1, 0, 6, 0);
            form.add(campos[i], g);
        }

        JLabel lblDesc = new JLabel("Descripción (opcional)");
        lblDesc.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        lblDesc.setForeground(TEXTO);
        g.gridy = 6;
        g.insets = new Insets(6, 0, 1, 0);
        form.add(lblDesc, g);

        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setBackground(CARD);
        txtDescripcion.setForeground(TEXTO_MUTED);
        txtDescripcion.setCaretColor(TEXTO);
        txtDescripcion.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
        txtDescripcion.setBorder(BorderFactory.createLineBorder(BORDE));
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(txtDescripcion);
        scroll.setBorder(BorderFactory.createLineBorder(BORDE));
        g.gridy = 7;
        g.insets = new Insets(1, 0, 6, 0);
        g.weighty = 1.0;
        g.fill = GridBagConstraints.BOTH;
        form.add(scroll, g);

        JPanel footer = new JPanel();
        footer.setBackground(new Color(15, 15, 15));
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDE),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));

        JButton btnSig = new JButton("Siguiente");
        btnSig.setBackground(BTN_ORO);
        btnSig.setForeground(new Color(15, 15, 15));
        btnSig.setOpaque(true);
        btnSig.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        btnSig.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        btnSig.setFocusPainted(false);
        btnSig.setBorderPainted(false);
        btnSig.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSig.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSig.setMaximumSize(new Dimension(430, 42));
        btnSig.addActionListener(e -> registrar());

        JButton btnVol = new JButton("Regresar");
        btnVol.setBackground(CARD);
        btnVol.setForeground(TEXTO_MUTED);
        btnVol.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        btnVol.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnVol.setFocusPainted(false);
        btnVol.setBorderPainted(false);
        btnVol.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVol.addActionListener(e
                -> control.mostrar(ControlVistas.pantallaMenuAdmin));

        footer.add(btnSig);
        footer.add(Box.createVerticalStrut(6));
        footer.add(btnVol);

        card.add(header, BorderLayout.NORTH);
        card.add(form, BorderLayout.CENTER);
        card.add(footer, BorderLayout.SOUTH);

        contenedor.add(card);
        return contenedor;
    }
    
 

    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El nombre de la barbería es obligatorio.",
                    "Campo incompleto", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "La dirección es obligatoria.",
                    "Campo incompleto", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (telefono.isEmpty() || !telefono.matches("[0-9]{10}")) {
            JOptionPane.showMessageDialog(this,
                    "El teléfono debe tener exactamente 10 dígitos.\nEjemplo: 6621234567",
                    "Teléfono inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        BarberiaDTO dto = new BarberiaDTO();
        dto.setNombre(nombre);
        dto.setDireccion(direccion);
        dto.setTelefono(telefono);
        dto.setDescripcion(descripcion);
        dto.setIdBarbero(GestorSesion.getClienteActivo().getId());
        dto.setActiva(true);
        
        barberiaRegistrada = facadeBarberia.registrar(dto);
        control.<PanelAsignarHorario>getPanel(ControlVistas.pantallaAsignarHorario)
                .setIdBarberia(barberiaRegistrada.getId());
        control.<PanelAsignarHorario>getPanel(ControlVistas.pantallaAsignarHorario)
                .setPantallaOrigen(ControlVistas.pantallaMenuAdmin);
        control.mostrar(ControlVistas.pantallaAsignarHorario);
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

    private JLabel etiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        l.setForeground(TEXTO_MUTED);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
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
        b.setBackground(BTN_ORO);
        b.setForeground(new Color(15, 15, 15));
        b.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        b.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
    
    private static class SoloNumeros extends javax.swing.text.DocumentFilter {

        private final int limite;

        SoloNumeros(int limite) {
            this.limite = limite;
        }

        @Override
        public void insertString(javax.swing.text.DocumentFilter.FilterBypass fb,
                int offset, String string, javax.swing.text.AttributeSet attr)
                throws javax.swing.text.BadLocationException {
            if (string != null && string.matches("[0-9]+")
                    && fb.getDocument().getLength() + string.length() <= limite) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(javax.swing.text.DocumentFilter.FilterBypass fb,
                int offset, int length, String text, javax.swing.text.AttributeSet attrs)
                throws javax.swing.text.BadLocationException {
            if (text != null && text.matches("[0-9]*")
                    && fb.getDocument().getLength() - length + text.length() <= limite) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}
